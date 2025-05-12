package com.example.e_commerceapp.data.repository

import android.util.Log
import com.example.e_commerceapp.ui.adapter.CategoryAdapter
import com.google.firebase.firestore.FirebaseFirestore
import java.util.Locale

class CategoryRepository {
    /*private lateinit var categoryAdapter: CategoryAdapter
    private val firestore = FirebaseFirestore.getInstance()

    private fun fetchCategoriesFromFirestore() {
        firestore.collection("categories")
            .get()
            .addOnSuccessListener { result ->
                val categories = result.toObjects<Locale.Category>()
                categoryAdapter.submitList(categories)
            }
            .addOnFailureListener { exception ->
                Log.e("HomeFragment", "Error fetching categories", exception)
            }
    }*/
}