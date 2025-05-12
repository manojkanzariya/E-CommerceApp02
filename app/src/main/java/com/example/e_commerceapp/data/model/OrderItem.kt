package com.example.e_commerceapp.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class OrderItem(
    var productId: String = "",
    var name: String = "",
    var price: Double = 0.0,
    var imageUrl: String = "",
    var quantity: Int = 1
) : Parcelable {
    fun toMap(): Map<String, Any> {
        return mapOf(
            "productId" to productId,
            "name" to name,
            "quantity" to quantity,
            "price" to price,
            "imageUrl" to imageUrl
        )
    }
}