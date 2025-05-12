package com.example.e_commerceapp.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import android.util.Log
import com.example.e_commerceapp.data.model.Product
import com.example.e_commerceapp.data.model.Review
import com.example.e_commerceapp.ui.adapter.RelatedProductsAdapter
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.Query
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ProductRepository {
    private val db = FirebaseFirestore.getInstance()

    fun getProducts(onResult: (List<Product>) -> Unit) {
        db.collection("products")
            .limit(15)
            .get()
            .addOnSuccessListener { documents ->
                val products = documents.mapNotNull { doc ->
                    try {
                        Product(
                            id = doc.id,
                            productId = doc.id,
                            name = doc.getString("name") ?: "",
                            price = doc.getDouble("price") ?: 0.0,
                            vendorId = doc.getString("vendorId") ?: run {
                                Log.e("ProductError", "Missing vendorId in product ${doc.id}")
                                ""
                            },
                            originalPrice = doc.getDouble("originalPrice") ?: 0.0,
                            shortDescription = doc.getString("shortDescription") ?: "",
                            fullDescription = doc.getString("fullDescription") ?: "",
                            rating = (doc.getDouble("rating") ?: 0.0).toFloat(),
                            ratingCount = (doc.getLong("ratingCount") ?: 0L).toInt(),
                            category = doc.getString("category") ?: "",
                            imageUrl = doc.getString("imageUrl") ?: "",
                            stock = (doc.getLong("stock") ?: 0L).toInt(),
                            quantity = 1, // Default quantity for cart
                            inStock = doc.getBoolean("inStock") ?: true,
                            timestamp = doc.getTimestamp("timestamp") ?: com.google.firebase.Timestamp.now()
                        )
                    } catch (e: Exception) {
                        Log.e("ProductMapping", "Error mapping product ${doc.id}", e)
                        null // Skip corrupted products
                    }
                }

                products.forEach {
                    if (it.vendorId.isBlank()) {
                        Log.w("ProductWarning", "Product ${it.id} has empty vendorId")
                    }
                }

                onResult(products)
                Log.d("ProductFetch", "Successfully loaded ${products.size} products")
            }
            .addOnFailureListener { exception ->
                Log.e("Firestore", "Error getting products", exception)
                onResult(emptyList())
            }
    }

    fun getRelatedProducts(category: String, excludeProductId: String, limit: Int, onResult: (List<Product>) -> Unit) {
        db.collection("products")
            .whereEqualTo("category", category)
            .whereNotEqualTo("id", excludeProductId)
            .limit(limit.toLong())
            .get()
            .addOnSuccessListener { documents ->
                val products = documents.map { it.toObject(Product::class.java) }
                onResult(products)
            }
            .addOnFailureListener { e ->
                Log.e("ProductRepository", "Error getting related products", e)
                onResult(emptyList())
            }
    }

    fun getProductReviews(productId: String, limit: Int, onResult: (List<Review>) -> Unit) {
        db.collection("reviews")
            .whereEqualTo("productId", productId)
            .orderBy("timestamp", Query.Direction.DESCENDING)
            .limit(limit.toLong())
            .get()
            .addOnSuccessListener { documents ->
                val reviews = documents.map { it.toObject(Review::class.java) }
                onResult(reviews)
            }
            .addOnFailureListener { e ->
                Log.e("ProductRepository", "Error getting reviews", e)
                onResult(emptyList())
            }
    }

    fun addProductReview(userId: String, userName: String, productId: String, rating: Float, comment: String, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        val review = hashMapOf(
            "userId" to userId,
            "userName" to userName,
            "productId" to productId,
            "rating" to rating,
            "comment" to comment,
            "date" to SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(Date()),
            "timestamp" to FieldValue.serverTimestamp()
        )

        db.collection("reviews")
            .add(review)
            .addOnSuccessListener {
                updateProductRating(productId, rating)
                onSuccess()
            }
            .addOnFailureListener { e ->
                onFailure(e)
            }
    }

    private fun updateProductRating(productId: String, newRating: Float) {
        val productRef = db.collection("products").document(productId)

        db.runTransaction { transaction ->
            val snapshot = transaction.get(productRef)
            val currentRating = snapshot.getDouble("rating")?.toFloat() ?: 0f
            val currentCount = snapshot.getLong("ratingCount")?.toInt() ?: 0
            val newAverage = ((currentRating * currentCount) + newRating) / (currentCount + 1)

            transaction.update(productRef,
                "rating", newAverage,
                "ratingCount", currentCount + 1
            )
        }.addOnFailureListener { e ->
            Log.e("ProductRepository", "Error updating product rating", e)
        }
    }
}
