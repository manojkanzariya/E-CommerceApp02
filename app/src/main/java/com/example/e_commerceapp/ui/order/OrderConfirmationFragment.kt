package com.example.e_commerceapp.ui.order

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.e_commerceapp.R
import com.example.e_commerceapp.data.model.DeliveryAddress
import com.example.e_commerceapp.data.model.Order
import com.example.e_commerceapp.data.repository.UserRepository
import com.example.e_commerceapp.databinding.FragmentOrderConfirmationBinding
import com.example.e_commerceapp.utils.OrderUtils
import com.example.e_commerceapp.utils.PriceUtils
import java.text.SimpleDateFormat
import java.util.*

class OrderConfirmationFragment : Fragment() {

    private var _binding: FragmentOrderConfirmationBinding? = null
    private val binding get() = _binding!!
    private var confirmationDetails: Order? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        confirmationDetails = arguments?.getParcelable("confirmationDetails")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentOrderConfirmationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupOrderDetails()
        setupContinueButton()
    }

    private fun setupOrderDetails() {
        confirmationDetails?.let { details ->
            binding.tvOrderNumber.text = details.orderId
            binding.tvOrderDate.text = OrderUtils.formatDate(details.date)
            binding.tvOrderTotal.text = PriceUtils.formatPrice(details.total)
            binding.tvDeliveryAddress.text = formatAddress(details.deliveryAddress)
            binding.tvDeliveryDate.text = details.estimatedDelivery
        } ?: run {
            // Handle missing details
            findNavController().popBackStack()
        }
    }

    private fun formatAddress(address: DeliveryAddress): String {
        return "${address.name}\n${address.phone}, ${address.city}, ${address.city} - ${address.pincode}"
    }

    private fun setupContinueButton() {
        binding.btnContinueShopping.setOnClickListener {
            // Navigate back to home screen
            findNavController().popBackStack(R.id.navigation_home, false)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}