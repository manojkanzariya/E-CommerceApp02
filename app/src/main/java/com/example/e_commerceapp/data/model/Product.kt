package com.example.e_commerceapp.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Product(
    var id: String = "",
    val name: String = "",
    val price: Double = 0.0,
    var vendorId : String = "",
    val productId : String = "",

    val originalPrice: Double = 0.0,

    val shortDescription: String = "",
    val fullDescription: String = "",

    val rating: Float = 0.0f,
    val ratingCount: Int = 0,

    val category: String = "",
    val imageUrl: String = "",

    var stock: Int = 1,
    val quantity: Int = 1,
    val inStock: Boolean = true,

    val timestamp: com.google.firebase.Timestamp = com.google.firebase.Timestamp.now()
) : Parcelable