package com.example.e_commerceapp.ui.product

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.e_commerceapp.data.model.Review
import com.example.e_commerceapp.databinding.FragmentProductReviewsBinding
import com.example.e_commerceapp.ui.adapter.ReviewsAdapter
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class ProductReviewsFragment : Fragment() {

    private var _binding: FragmentProductReviewsBinding? = null
    private val binding get() = _binding!!
    private lateinit var reviewsAdapter: ReviewsAdapter
    private val db = FirebaseFirestore.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductReviewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        loadProductReviews()
    }

    private fun setupRecyclerView() {
        reviewsAdapter = ReviewsAdapter()
        binding.recyclerViewReviews.apply {
            adapter = reviewsAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun loadProductReviews() {
        val productId = arguments?.getString("productId") ?: run {
            showToast("Product ID not found")
            parentFragmentManager.popBackStack()
            return
        }

        db.collection("reviews")
            .whereEqualTo("productId", productId)
            .orderBy("timestamp", Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener { documents ->
                val reviews = documents.map { it.toObject(Review::class.java) }
                reviewsAdapter.submitList(reviews)
            }
            .addOnFailureListener { e ->
                showToast("Failed to load reviews")
                Log.e("Reviews", "Error: ", e)
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