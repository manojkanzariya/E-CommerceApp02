package com.example.e_commerceapp.ui.product

import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.e_commerceapp.R
import com.example.e_commerceapp.data.model.Product
import com.example.e_commerceapp.data.repository.CartManager
import com.example.e_commerceapp.data.repository.ProductRepository
import com.example.e_commerceapp.data.repository.UserRepository
import com.example.e_commerceapp.data.repository.WishlistRepository
import com.example.e_commerceapp.databinding.DialogAddReviewBinding
import com.example.e_commerceapp.databinding.FragmentProductDetailsBinding
import com.example.e_commerceapp.ui.adapter.RelatedProductsAdapter
import com.example.e_commerceapp.ui.adapter.ReviewsAdapter
import com.example.e_commerceapp.ui.payment.PaymentFragment
import com.example.e_commerceapp.ui.profile.AddAddressFragment
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

class ProductDetailsFragment : Fragment() {

    private var _binding: FragmentProductDetailsBinding? = null
    private val binding get() = _binding!!
    private lateinit var relatedProductsAdapter: RelatedProductsAdapter
    private lateinit var reviewsAdapter: ReviewsAdapter
    private var product: Product? = null
    private var isInWishlist = false
    private val wishlistRepository = WishlistRepository()

    private val repository = ProductRepository()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentProductDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        product = arguments?.getParcelable("product")

        if (product == null) {
            showToast("Product not found")
            parentFragmentManager.popBackStack()
            return
        }

        setupUI()
        setupRecyclerViews()
        setupListeners()
        fetchRelatedProducts()
        fetchProductReviews()
        setupWishlistButton()
        checkWishlistStatus()
    }

    // --------------------- UI SETUP ------------------------

    private fun setupWishlistButton() {
        binding.ivWishlist.setOnClickListener {
            product?.let { product ->
                if (isInWishlist) {
                    removeFromWishlist(product)
                } else {
                    addToWishlist(product)
                }
            }
        }
    }

    private fun checkWishlistStatus() {
        product?.let { product ->
            lifecycleScope.launch {
                isInWishlist = wishlistRepository.isProductInWishlist(product.id)
                updateWishlistIcon()
            }
        }
    }

    private fun addToWishlist(product: Product) {
        lifecycleScope.launch {
            try {
                wishlistRepository.addToWishlist(product)
                isInWishlist = true
                updateWishlistIcon()
                showEnhancedToast("✓ Added to wishlist")
            } catch (e: Exception) {
                showToast("Failed to add to wishlist")
            }
        }
    }

    private fun removeFromWishlist(product: Product) {
        lifecycleScope.launch {
            try {
                wishlistRepository.removeFromWishlist(product.id)
                isInWishlist = false
                updateWishlistIcon()
                showEnhancedToast("Removed from wishlist")
            } catch (e: Exception) {
                showToast("Failed to remove from wishlist")
            }
        }
    }

    private fun updateWishlistIcon() {
        binding.ivWishlist.setImageResource(
            if (isInWishlist) R.drawable.ic_wishlist_filled
            else R.drawable.ic_wishlist_outline
        )
    }

    private fun setupUI() {
        product?.let { bindProductDetails(it) }

        binding.root.post {
            binding.root.alpha = 0f
            binding.root.animate().alpha(1f).setDuration(300).start()
        }
    }

    private fun bindProductDetails(product: Product) = with(binding) {
        Log.d("ProductCheck", product.toString())

        tvProductName.text = product.name
        tvProductPrice.text = "₹${product.price}"

        // Original price visibility
        if (product.originalPrice > product.price) {
            tvOriginalPrice.apply {
                text = "₹${product.originalPrice}"
                paintFlags = paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                visibility = View.VISIBLE
            }
        } else {
            tvOriginalPrice.visibility = View.GONE
        }

        tvStockStatus.text = if (product.inStock) "In Stock" else "Out of Stock"

        // Description handling
        tvProductDescription.text = product.shortDescription.ifEmpty { "No description available." }
        tvDescription.text = product.fullDescription.ifEmpty { "No additional info provided." }

        // Rating
        ratingBar.rating = product.rating
        ratingBarSummary.rating = product.rating
        tvRatingCount.text = "(${product.ratingCount})"
        tvReviewCount.text = "Based on ${product.ratingCount} reviews"

        Glide.with(this@ProductDetailsFragment)
            .load(product.imageUrl)
            .placeholder(R.drawable.ic_placeholder)
            .error(R.drawable.ic_placeholder)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(ivProductImage)
    }


    // --------------------- RECYCLERS ------------------------

    private fun setupRecyclerViews() {
        relatedProductsAdapter = RelatedProductsAdapter { clickedProduct ->
            val fragment = ProductDetailsFragment().apply {
                arguments = Bundle().apply {
                    putParcelable("product", clickedProduct)
                }
            }
            parentFragmentManager.beginTransaction()
                .replace(R.id.nav_host_fragment, fragment)
                .addToBackStack(null)
                .commit()
        }

        binding.rvRelatedProducts.apply {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
            adapter = relatedProductsAdapter
            itemAnimator = DefaultItemAnimator()
        }

        reviewsAdapter = ReviewsAdapter()
        binding.rvReviews.adapter = reviewsAdapter
    }

    // --------------------- FIREBASE DATA ------------------------

    private fun fetchRelatedProducts() {
        val productId = product?.id ?: return
        val category = product?.category ?: return

        repository.getRelatedProducts(category, productId, 5) { products ->
            relatedProductsAdapter.updateList(products)
        }
    }

    private fun fetchProductReviews() {
        val productId = product?.id ?: return
        repository.getProductReviews(productId, 10) { reviews ->
            reviewsAdapter.submitList(reviews)
        }
    }

    // --------------------- LISTENERS ------------------------

    private fun setupListeners() {
        binding.btnAddToCart.setOnClickListener {
            animateButton(binding.btnAddToCart) {
                product?.let { product ->
                    lifecycleScope.launch {
                        CartManager.addToCart(product)
                        showEnhancedToast("✓ ${product.name} added to cart")
                    }
                }
            }
        }

        binding.btnBuyNow.setOnClickListener {
            animateButton(binding.btnBuyNow) {
                product?.let {
                    val fragment = if (!UserRepository.hasAddress()) {
                        AddAddressFragment()
                    } else {
                        PaymentFragment()
                    }

                    parentFragmentManager.beginTransaction()
                        .replace(R.id.nav_host_fragment, fragment)
                        .addToBackStack(null)
                        .commit()
                }
            }
        }

        binding.btnAddReview.setOnClickListener {
            animateButton(binding.btnAddReview) {
                showAddReviewDialog()
            }
        }

        binding.btnViewAllReviews.setOnClickListener {
            animateButton(binding.btnViewAllReviews) {
                product?.let {
                    val fragment = ProductReviewsFragment().apply {
                        arguments = Bundle().apply {
                            putString("productId", it.id)
                        }
                    }
                    parentFragmentManager.beginTransaction()
                        .replace(R.id.nav_host_fragment, fragment)
                        .addToBackStack(null)
                        .commit()
                } ?: showToast("Product ID not found")
            }
        }
    }

    // --------------------- DIALOG ------------------------

    private fun showAddReviewDialog() {
        val dialogBinding = DialogAddReviewBinding.inflate(layoutInflater)

        val dialog = AlertDialog.Builder(requireContext())
            .setView(dialogBinding.root)
            .create()

        //dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()

        dialogBinding.btnSubmitReview.setOnClickListener {
            val rating = dialogBinding.ratingBarInput.rating
            val comment = dialogBinding.etReviewText.text.toString().trim()

            if (rating == 0f || comment.isEmpty()) {
                showToast("Please provide rating and review")
                return@setOnClickListener
            }

            val user = FirebaseAuth.getInstance().currentUser
            if (user == null) {
                showToast("Login required to write a review")
                return@setOnClickListener
            }

            val productId = product?.id ?: return@setOnClickListener

            repository.addProductReview(
                userId = user.uid,
                userName = user.displayName ?: "Anonymous",
                productId = productId,
                rating = rating,
                comment = comment,
                onSuccess = {
                    showToast("Review added successfully!")
                    fetchProductReviews()
                    dialog.dismiss()
                },
                onFailure = {
                    showToast("Failed to add review")
                }
            )
        }
    }

    // --------------------- UTILS ------------------------

    private fun animateButton(view: View, action: () -> Unit) {
        view.animate().scaleX(0.95f).scaleY(0.95f).setDuration(100).withEndAction {
            view.animate().scaleX(1f).scaleY(1f).setDuration(100).start()
            action()
        }.start()
    }

    private fun showEnhancedToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).apply {
            view?.background?.setTint(ContextCompat.getColor(requireContext(), R.color.success_green))
            view?.findViewById<TextView>(android.R.id.message)?.setTextColor(Color.WHITE)
            show()
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}