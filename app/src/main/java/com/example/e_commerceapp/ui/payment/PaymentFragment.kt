package com.example.e_commerceapp.ui.payment

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.e_commerceapp.R
import com.example.e_commerceapp.data.model.CartItem
import com.example.e_commerceapp.data.model.VendorPaymentInfo
import com.example.e_commerceapp.data.repository.CartManager
import com.example.e_commerceapp.data.repository.OrderRepository
import com.example.e_commerceapp.databinding.FragmentPaymentBinding
import com.google.firebase.firestore.FirebaseFirestore

class PaymentFragment : Fragment() {
    private var _binding: FragmentPaymentBinding? = null
    private val binding get() = _binding!!
    private val db = FirebaseFirestore.getInstance()

    companion object {
        private const val UPI_PAYMENT_REQUEST_CODE = 1001
        private const val DEFAULT_PAYMENT_METHOD = "UPI"
    }

    private var selectedPaymentMethod: String = DEFAULT_PAYMENT_METHOD
    private val cartAmount: Double by lazy {
        val subtotal = CartManager.getCartSubtotal()
        val shipping = if (subtotal > 50) 0.0 else 5.99
        subtotal + shipping
    }

    // Map of vendor IDs to their payment details
    private val vendorPaymentDetails = mutableMapOf<String, VendorPaymentInfo>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentPaymentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadVendorPaymentDetails()
        setupUI()
    }

    private fun loadVendorPaymentDetails() {
        val cartItems = CartManager.getCartItems()
        val vendorIds = cartItems.map { it.product.vendorId }.distinct()

        vendorIds.forEach { vendorId ->
            if (vendorId.isNotBlank()) {
                db.collection("vendor_payments").document(vendorId)
                    .get()
                    .addOnSuccessListener { document ->
                        if (document.exists()) {
                            val paymentInfo = VendorPaymentInfo(
                                upiId = document.getString("upiId") ?: "",
                                upiEnabled = document.getBoolean("upiEnabled") ?: false,
                                accountNumber = document.getString("accountNumber") ?: "",
                                bankName = document.getString("bankName") ?: "",
                                accountHolderName = document.getString("accountHolderName") ?: "",
                                ifscCode = document.getString("ifscCode") ?: "",
                                bankTransferEnabled = document.getBoolean("bankTransferEnabled") ?: false
                            )
                            vendorPaymentDetails[vendorId] = paymentInfo
                        } else {
                            Log.e("PaymentFragment", "No payment details found for vendor: $vendorId")
                        }
                    }
                    .addOnFailureListener { e ->
                        Log.e("PaymentFragment", "Error loading payment details for vendor $vendorId", e)
                    }
            }
        }
    }

    private fun setupUI() {
        with(binding) {
            textOrderTotal.text = getString(R.string.price_format, cartAmount)

            radioGroupPayment.setOnCheckedChangeListener { _, checkedId ->
                selectedPaymentMethod = when (checkedId) {
                    R.id.radioUpi -> "UPI"
                    R.id.radioGooglePay -> "Google Pay"
                    R.id.radioPaytm -> "Paytm"
                    R.id.radioPhonePe -> "PhonePe"
                    R.id.radioCod -> "Cash on Delivery"
                    else -> DEFAULT_PAYMENT_METHOD
                }
                updatePaymentButtonState()
            }

            btnProceedToPay.setOnClickListener {
                if (selectedPaymentMethod == "Cash on Delivery") {
                    processCashOnDelivery()
                } else {
                    validateVendorPaymentsAndProceed()
                }
            }
        }
    }

    private fun validateVendorPaymentsAndProceed() {
        val cartItems = CartManager.getCartItems()
        val vendorsWithoutPayment = mutableListOf<String>()

        // Check if all vendors have valid payment methods
        cartItems.forEach { item ->
            val vendorId = item.product.vendorId
            if (vendorId.isNotBlank()) {
                val paymentInfo = vendorPaymentDetails[vendorId]
                if (paymentInfo == null ||
                    (!paymentInfo.upiEnabled && !paymentInfo.bankTransferEnabled)) {
                    vendorsWithoutPayment.add(item.product.vendorId ?: "Unknown Vendor")
                }
            }
        }

        if (vendorsWithoutPayment.isNotEmpty()) {
            showErrorDialog(
                "The following vendors don't have payment setup:\n" +
                        vendorsWithoutPayment.joinToString("\n") +
                        "\n\nPlease contact them or choose Cash on Delivery"
            )
        } else {
            initiatePaymentToVendors()
        }
    }

    private fun initiatePaymentToVendors() {
        val cartItems = CartManager.getCartItems()
        val vendorAmounts = calculateVendorAmounts(cartItems)

        if (selectedPaymentMethod == "UPI" ||
            selectedPaymentMethod == "Google Pay" ||
            selectedPaymentMethod == "Paytm" ||
            selectedPaymentMethod == "PhonePe") {

            // For UPI payments, we'll pay each vendor separately
            payVendorsViaUpi(vendorAmounts)
        } else {
            // For other online payment methods (would need gateway integration)
            showConfirmationDialog(
                title = "Confirm Payment",
                message = "You will be redirected to payment gateway to pay ${getString(R.string.price_format, cartAmount)}",
                positiveAction = { createOrderAndNavigate() }
            )
        }
    }

    private fun calculateVendorAmounts(cartItems: List<CartItem>): Map<String, Double> {
        val vendorAmounts = mutableMapOf<String, Double>()

        cartItems.forEach { item ->
            val vendorId = item.product.vendorId
            if (vendorId.isNotBlank()) {
                val amount = item.product.price * item.quantity
                vendorAmounts[vendorId] = (vendorAmounts[vendorId] ?: 0.0) + amount
            }
        }

        // Add shipping cost proportionally (simplified - could be improved)
        val subtotal = CartManager.getCartSubtotal()
        val shipping = if (subtotal > 50) 0.0 else 5.99
        if (shipping > 0) {
            vendorAmounts.forEach { (vendorId, amount) ->
                val shippingShare = (amount / subtotal) * shipping
                vendorAmounts[vendorId] = amount + shippingShare
            }
        }

        return vendorAmounts
    }

    private fun payVendorsViaUpi(vendorAmounts: Map<String, Double>) {
        val vendors = vendorAmounts.keys.toList()
        val currentVendorIndex = 0

        fun processNextVendor(index: Int) {
            if (index >= vendors.size) {
                // All vendors paid
                createOrderAndNavigate()
                return
            }

            val vendorId = vendors[index]
            val amount = vendorAmounts[vendorId] ?: 0.0
            val paymentInfo = vendorPaymentDetails[vendorId]

            if (paymentInfo?.upiEnabled == true && amount > 0) {
                showVendorPaymentDialog(
                    vendorName = CartManager.getCartItems()
                        .firstOrNull { it.product.vendorId == vendorId }?.product?.vendorId
                        ?: "Vendor",
                    amount = amount,
                    upiId = paymentInfo.upiId,
                    onPaymentComplete = { success ->
                        if (success) {
                            processNextVendor(index + 1)
                        } else {
                            showErrorDialog("Payment to vendor failed. Please try again.")
                        }
                    }
                )
            } else {
                // Skip to next vendor if UPI not enabled or amount is 0
                processNextVendor(index + 1)
            }
        }

        processNextVendor(currentVendorIndex)
    }

    private fun showVendorPaymentDialog(
        vendorName: String,
        amount: Double,
        upiId: String,
        onPaymentComplete: (Boolean) -> Unit
    ) {
        AlertDialog.Builder(requireContext()).apply {
            setTitle("Pay $vendorName")
            setMessage("Amount: ${getString(R.string.price_format, amount)}\nUPI ID: $upiId")
            setPositiveButton("Pay Now") { _, _ ->
                val upiUri = buildUpiPaymentUri(upiId, amount, vendorName)
                val intent = Intent(Intent.ACTION_VIEW).apply { data = upiUri }
                val chooser = Intent.createChooser(intent, "Pay $vendorName")

                if (chooser.resolveActivity(requireActivity().packageManager) != null) {
                    startActivityForResult(chooser, UPI_PAYMENT_REQUEST_CODE)
                    onPaymentComplete(true)
                } else {
                    showErrorDialog(getString(R.string.no_upi_app_found))
                    onPaymentComplete(false)
                }
            }
            setNegativeButton("Cancel") { _, _ -> onPaymentComplete(false) }
            setCancelable(false)
            show()
        }
    }

    private fun buildUpiPaymentUri(upiId: String, amount: Double, vendorName: String): Uri {
        return Uri.parse("upi://pay").buildUpon()
            .appendQueryParameter("pa", upiId)
            .appendQueryParameter("pn", vendorName)
            .appendQueryParameter("mc", "")
            .appendQueryParameter("tid", "TXN${System.currentTimeMillis()}")
            .appendQueryParameter("tr", "TXN${System.currentTimeMillis()}")
            .appendQueryParameter("tn", "Payment to $vendorName")
            .appendQueryParameter("am", amount.toString())
            .appendQueryParameter("cu", "INR")
            .build()
    }

    private fun updatePaymentButtonState() {
        binding.btnProceedToPay.text = if (selectedPaymentMethod == "Cash on Delivery") {
            getString(R.string.place_order)
        } else {
            getString(R.string.proceed_to_pay)
        }
    }

    private fun processCashOnDelivery() {
        showConfirmationDialog(
            title = getString(R.string.confirm_order),
            message = getString(R.string.cod_confirmation_message, cartAmount),
            positiveAction = { createOrderAndNavigate() }
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == UPI_PAYMENT_REQUEST_CODE) {
            val response = data?.getStringExtra("response") ?: ""
            val upiData = parseUpiResponse(response)
            val status = upiData["status"]?.equals("success", ignoreCase = true) ?: false

            if (status) {
                showPaymentResultDialog(
                    success = true,
                    message = getString(R.string.payment_success_message, upiData["teared"] ?: "N/A")
                )
            } else {
                showPaymentResultDialog(
                    success = false,
                    message = getString(R.string.payment_failed_message)
                )
            }
        }
    }

    private fun parseUpiResponse(response: String): Map<String, String> {
        return response.split("&").associate {
            val parts = it.split("=")
            if (parts.size == 2) parts[0].lowercase() to parts[1] else "" to ""
        }.filter { it.key.isNotEmpty() }
    }

    private fun showPaymentResultDialog(success: Boolean, message: String) {
        AlertDialog.Builder(requireContext()).apply {
            setTitle(if (success) R.string.payment_success else R.string.payment_failed)
            setMessage(message)
            setPositiveButton(R.string.ok) { dialog, _ ->
                dialog.dismiss()
                if (success) createOrderAndNavigate()
            }
            setCancelable(false)
            show()
        }
    }

    private fun showConfirmationDialog(title: String, message: String, positiveAction: () -> Unit) {
        AlertDialog.Builder(requireContext()).apply {
            setTitle(title)
            setMessage(message)
            setPositiveButton(R.string.confirm) { dialog, _ ->
                dialog.dismiss()
                positiveAction()
            }
            setNegativeButton(R.string.cancel) { dialog, _ -> dialog.dismiss() }
            show()
        }
    }

    private fun showErrorDialog(message: String) {
        AlertDialog.Builder(requireContext()).apply {
            setTitle(R.string.error)
            setMessage(message)
            setPositiveButton(R.string.ok) { dialog, _ -> dialog.dismiss() }
            show()
        }
    }

    private fun createOrderAndNavigate() {
        confirmAndPlaceOrder()
        val bundle = Bundle().apply {
            putDouble("totalAmount", cartAmount)
        }
        findNavController().navigate(R.id.action_paymentFragment_to_orderConfirmationFragment, bundle)
    }

    private fun confirmAndPlaceOrder() {
        val cartItems = CartManager.getCartItems().map { it.product }
        val paymentMethod = selectedPaymentMethod

        val total = CartManager.getCartSubtotal().let {
            if (it > 50) it else it + 5.99
        }
        cartItems.forEach {
            Log.d("CartDebug", "Product: ${it.name}, VendorID: ${it.vendorId}")
            if (it.vendorId.isBlank()) {
                Toast.makeText(context, "Error: Product ${it.name} has no vendor", Toast.LENGTH_LONG).show()
            }
        }

        // Group items by vendor for order processing
        val vendorOrders = cartItems.groupBy { it.vendorId }

        vendorOrders.forEach { (vendorId, products) ->
            if (vendorId.isBlank()) {
                Toast.makeText(context, "Error: Some products have no vendor", Toast.LENGTH_LONG).show()
                return@forEach
            }

            val vendorTotal = products.sumOf { it.price }
            val vendorShipping = if (total > 50) 0.0 else (5.99 * (vendorTotal / total))

            OrderRepository.placeOrder(
                cartItems = products,
                totalAmount = vendorTotal + vendorShipping,
                paymentMethod = paymentMethod,
                vendorId = vendorId,
                onSuccess = { orderId, confirmationDetails ->
                    // Navigate with the prepared details
                    findNavController().navigate(
                        R.id.action_paymentFragment_to_orderConfirmationFragment,
                        bundleOf("confirmationDetails" to confirmationDetails)
                    )
                    CartManager.clearCart()
                    Log.d("OrderPlaced", "Order $orderId placed with vendor $vendorId")
                },
                onFailure = {
                    Toast.makeText(requireContext(), "Failed to place order with vendor $vendorId", Toast.LENGTH_SHORT).show()
                }
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
