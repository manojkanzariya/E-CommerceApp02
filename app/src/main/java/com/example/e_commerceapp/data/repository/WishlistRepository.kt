package com.example.e_commerceapp.data.repository

import com.example.e_commerceapp.data.model.Product
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class WishlistRepository {
    private val firestore = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    suspend fun getWishlistProducts(): List<Product> {
        return try {
            val userId = auth.currentUser?.uid ?: throw Exception("User not authenticated")

            val snapshot = firestore.collection("users")
                .document(userId)
                .collection("wishlist")
                .get()
                .await()

            snapshot.documents.mapNotNull { document ->
                document.toObject(Product::class.java)?.apply {
                    // Ensure the document ID is set as product ID if needed
                    id = document.id
                }
            }
        } catch (e: Exception) {
            throw Exception("Failed to fetch wishlist: ${e.message}")
        }
    }

    suspend fun addToWishlist(product: Product) {
        try {
            val userId = auth.currentUser?.uid ?: throw Exception("User not authenticated")
            firestore.collection("users")
                .document(userId)
                .collection("wishlist")
                .document(product.id)
                .set(product)
                .await()
        } catch (e: Exception) {
            throw Exception("Failed to add to wishlist: ${e.message}")
        }
    }

    suspend fun removeFromWishlist(productId: String) {
        try {
            val userId = auth.currentUser?.uid ?: throw Exception("User not authenticated")
            firestore.collection("users")
                .document(userId)
                .collection("wishlist")
                .document(productId)
                .delete()
                .await()
        } catch (e: Exception) {
            throw Exception("Failed to remove from wishlist: ${e.message}")
        }
    }

    suspend fun isProductInWishlist(productId: String): Boolean {
        return try {
            val userId = auth.currentUser?.uid ?: throw Exception("User not authenticated")
            val snapshot = firestore.collection("users")
                .document(userId)
                .collection("wishlist")
                .document(productId)
                .get()
                .await()
            snapshot.exists()
        } catch (e: Exception) {
            throw Exception("Failed to check wishlist: ${e.message}")
        }
    }
}