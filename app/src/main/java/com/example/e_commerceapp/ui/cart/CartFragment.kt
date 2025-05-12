package com.example.e_commerceapp.ui.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.e_commerceapp.R
import com.example.e_commerceapp.data.repository.CartManager
import com.example.e_commerceapp.data.repository.UserRepository
import com.example.e_commerceapp.databinding.FragmentCartBinding
import com.example.e_commerceapp.ui.adapter.CartAdapter
import com.example.e_commerceapp.utils.PriceUtils

class CartFragment : Fragment() {

    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: CartAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        setupEmptyCartButton()
    }

    private fun setupViews() {
        setupCartRecyclerView()
        setupCheckoutButton()
        updateCartSummary()
        checkEmptyState()
    }

    private fun setupEmptyCartButton() {
        binding.includeEmptyCart.btnContinueShopping.setOnClickListener {
            // Navigate to home or shop screen
            findNavController().navigate(R.id.action_navigation_cart_to_navigation_home)
        }
    }

    private fun setupCartRecyclerView() {
        val items = CartManager.getCartItems()
        adapter = CartAdapter(
            items = items,
            onUpdate = {
                updateCartSummary()
                checkEmptyState()
            },
            onItemRemoved = { item ->
                CartManager.removeFromCart(item)
                adapter.notifyDataSetChanged()
                checkEmptyState()
            },
            onQuantityChanged = { item, newQuantity ->
                CartManager.updateQuantity(item, newQuantity)
                adapter.notifyDataSetChanged()
                checkEmptyState()
            }
        )

        binding.rvCartItems.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@CartFragment.adapter
            addItemDecoration(
                DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL)
            )
        }
        checkEmptyState()
    }

    private fun setupCheckoutButton() {
        binding.btnCheckout.setOnClickListener {
            if (CartManager.getCartItems().isEmpty()) {
                showToast(getString(R.string.cart_empty_message))
                return@setOnClickListener
            }

            if (!UserRepository.hasAddress()) {
                // If no address, navigate to Add Address screen
                findNavController().navigate(R.id.action_navigation_cart_to_addAddressFragment)
            } else {
                // Proceed to Payment Screen
                findNavController().navigate(R.id.action_navigation_cart_to_paymentFragment)
            }
        }
    }


    private fun updateCartSummary() {
        val subtotal = CartManager.getCartSubtotal()
        val shipping = if (subtotal > 50) 0.0 else 5.99

        binding.apply {
            tvSubtotal.text = PriceUtils.formatPrice(subtotal)
            tvShipping.text = if (shipping == 0.0) {
                getString(R.string.free_shipping)
            } else {
                PriceUtils.formatPrice(shipping)
            }
            tvTotalPrice.text = PriceUtils.formatPrice(subtotal + shipping)
        }
    }

    private fun checkEmptyState() {
        binding.apply {
            if (CartManager.getCartItems().isEmpty()) {
                rvCartItems.visibility = View.GONE
                emptyStateView.visibility = View.VISIBLE
                btnCheckout.isEnabled = false
                btnCheckout.alpha = 0.5f
            } else {
                rvCartItems.visibility = View.VISIBLE
                emptyStateView.visibility = View.GONE
                btnCheckout.isEnabled = true
                btnCheckout.alpha = 1f
            }
        }
    }

    private fun showToast(message: String?) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}