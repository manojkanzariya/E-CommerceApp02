package com.example.e_commerceapp.ui.order

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.e_commerceapp.R
import com.example.e_commerceapp.data.model.DeliveryAddress
import com.example.e_commerceapp.data.model.Order
import com.example.e_commerceapp.data.model.OrderStatus
import com.example.e_commerceapp.data.model.Product
import com.example.e_commerceapp.databinding.FragmentOrderDetailsBinding
import com.example.e_commerceapp.ui.adapter.OrderProductAdapter
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class OrderDetailsFragment : Fragment() {
    private var _binding: FragmentOrderDetailsBinding? = null
    private val binding get() = _binding!!
    private lateinit var productAdapter: OrderProductAdapter
    private lateinit var currentOrder: Order
    private val db = FirebaseFirestore.getInstance()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentOrderDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val orderId = arguments?.getString("ORDER_ID")
        Log.d("OrderDetailsFragment", "Received Order ID: $orderId")

        if (orderId != null) {
            loadOrderDetails(orderId)
        } else {
            if (isAdded) {
                Toast.makeText(requireContext(), "Order not found", Toast.LENGTH_SHORT).show()
                parentFragmentManager.popBackStack()
            }
        }
    }

    private fun loadOrderDetails(orderId: String) {
        viewLifecycleOwner.lifecycleScope.launch {
            try {
                val snapshot = db.collection("orders")
                    .document(orderId)
                    .get()
                    .await()

                if (!isAdded || _binding == null) return@launch

                if (snapshot.exists()) {
                    val doc = snapshot

                    val deliveryAddressField = doc.get("deliveryAddress")
                    val deliveryAddress = when (deliveryAddressField) {
                        is Map<*, *> -> {
                            DeliveryAddress(
                                name = deliveryAddressField["name"] as? String ?: "",
                                phone = deliveryAddressField["phone"] as? String ?: "",
                                address = deliveryAddressField["address"] as? String ?: "",
                                city = deliveryAddressField["city"] as? String ?: "",
                                pincode = deliveryAddressField["pincode"] as? String ?: ""
                            )
                        }
                        is String -> DeliveryAddress(address = deliveryAddressField)
                        else -> DeliveryAddress()
                    }

                    val orderItems = (doc.get("items") as? List<Map<String, Any>>)?.map { itemMap ->
                        Product(
                            id = itemMap["productId"] as? String ?: "",
                            name = itemMap["name"] as? String ?: "",
                            price = (itemMap["price"] as? Number)?.toDouble() ?: 0.0,
                            quantity = (itemMap["quantity"] as? Number)?.toInt() ?: 1,
                            imageUrl = itemMap["imageUrl"] as? String ?: "",
                            vendorId = ""
                        )
                    } ?: emptyList()

                    currentOrder = Order(
                        id = doc.id,
                        orderId = doc.getString("orderId") ?: "",
                        userId = doc.getString("userId") ?: "",
                        vendorId = doc.getString("vendorId") ?: "",
                        productId = "",
                        quantity = 1,
                        date = doc.getLong("date") ?: 0L,
                        total = doc.getDouble("total") ?: 0.0,
                        timestamp = doc.getTimestamp("timestamp") ?: com.google.firebase.Timestamp.now(),
                        paymentMethod = doc.getString("paymentMethod") ?: "",
                        paymentStatus = doc.getString("paymentStatus") ?: "",
                        estimatedDelivery = doc.getString("estimatedDelivery") ?: "",
                        status = OrderStatus.fromString(doc.getString("status") ?: "") ?: OrderStatus.PENDING,
                        products = orderItems,
                        deliveryAddress = deliveryAddress
                    )

                    if (isAdded && _binding != null) {
                        bindOrderDetails(currentOrder)
                    }
                } else {
                    if (isAdded) {
                        Toast.makeText(requireContext(), "Order not found", Toast.LENGTH_SHORT).show()
                        parentFragmentManager.popBackStack()
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                if (isAdded) {
                    Toast.makeText(requireContext(), "Failed to load order details", Toast.LENGTH_SHORT).show()
                    parentFragmentManager.popBackStack()
                }
            }
        }
    }

    private fun bindOrderDetails(order: Order) {
        if (!isAdded || _binding == null) return

        binding.tvOrderId.text = "Order ID: ${order.orderId}"
        binding.tvOrderDate.text = "Date: ${SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(Date(order.date))}"
        binding.tvOrderTotal.text = "₹${order.total}"
        binding.tvOrderStatus.text = order.status.displayName
        binding.tvOrderStatus.setBackgroundResource(getStatusBackground(order.status))

        binding.tvEstimatedDelivery.text = "Estimated Delivery: ${order.estimatedDelivery}"
        binding.tvDeliveryName1.text = order.deliveryAddress.name
        binding.tvDeliveryPhone1.text = order.deliveryAddress.phone
        binding.tvDeliveryFullAddress1.text = "${order.deliveryAddress.address}\n${order.deliveryAddress.city}, ${order.deliveryAddress.pincode}"

        binding.tvPaymentMethod1.text = "Payment Method: ${order.paymentMethod}"
        binding.tvPaymentStatus1.text = "Payment Status: ${order.paymentStatus}"

        productAdapter = OrderProductAdapter(order.products)
        binding.rvOrderProducts.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = productAdapter
        }

        binding.btnCancelOrder.visibility =
            if (order.status == OrderStatus.PENDING || order.status == OrderStatus.PROCESSING)
                View.VISIBLE else View.GONE

        binding.btnTrackOrder.setOnClickListener {
            // TODO: Track order action
        }

        binding.btnNeedHelp.setOnClickListener {
            // TODO: Help action
        }

        binding.btnCancelOrder.setOnClickListener {
            showCancelConfirmationDialog()
        }
    }

    private fun showCancelConfirmationDialog() {
        if (!isAdded || _binding == null) return
        androidx.appcompat.app.AlertDialog.Builder(requireContext())
            .setTitle("Cancel Order")
            .setMessage("Are you sure you want to cancel this order?")
            .setPositiveButton("Yes") { _, _ -> cancelOrder() }
            .setNegativeButton("No", null)
            .show()
    }

    private fun cancelOrder() {
        viewLifecycleOwner.lifecycleScope.launch {
            try {
                db.collection("orders")
                    .document(currentOrder.id)
                    .update("status", OrderStatus.CANCELLED.toString())
                    .await()

                if (isAdded && _binding != null) {
                    Toast.makeText(requireContext(), "Order cancelled successfully", Toast.LENGTH_SHORT).show()
                    currentOrder.status = OrderStatus.CANCELLED
                    bindOrderDetails(currentOrder)
                }
            } catch (e: Exception) {
                if (isAdded) {
                    Toast.makeText(requireContext(), "Failed to cancel order: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun getStatusBackground(status: OrderStatus): Int {
        return when (status) {
            OrderStatus.PENDING -> R.drawable.bg_order_status_pending
            OrderStatus.PROCESSING -> R.drawable.bg_order_status_processing
            OrderStatus.SHIPPED -> R.drawable.bg_order_status_shipped
            OrderStatus.DELIVERED -> R.drawable.bg_order_status_delivered
            OrderStatus.CANCELLED -> R.drawable.bg_order_status_cancelled
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance(orderId: String): OrderDetailsFragment {
            return OrderDetailsFragment().apply {
                arguments = Bundle().apply {
                    putString("ORDER_ID", orderId)
                }
            }
        }
    }
}
