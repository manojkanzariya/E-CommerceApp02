package com.example.e_commerceapp.data.model

import android.os.Parcelable
import androidx.annotation.Keep
import com.example.e_commerceapp.R
import com.google.firebase.Timestamp
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class Order(
    var id: String = "",
    var orderId: String = "",
    var userId: String = "",
    var productId: String = "",
    var vendorId: String = "",

    var quantity: Int? = 1,
    var date: Long = 0L,
    var total: Double = 0.0,
    var items: List<OrderItem> = emptyList(),
    var products: List<Product> = emptyList(),
    var timestamp:Timestamp = Timestamp.now(),

    var deliveryAddress: DeliveryAddress = DeliveryAddress(),
    var estimatedDelivery: String = "",

    var paymentMethod: String = "",
    var paymentStatus: String = "",
    var status: OrderStatus = OrderStatus.PENDING

) : Parcelable

enum class OrderStatus(val displayName: String, val colorRes: Int) {
    PENDING("Pending", R.color.status_pending),
    PROCESSING("Processing", R.color.status_processing),
    SHIPPED("Shipped", R.color.status_shipped),
    DELIVERED("Delivered", R.color.status_delivered),
    CANCELLED("Cancelled", R.color.status_cancelled);

    companion object {
        fun fromString(value: String): OrderStatus? {
            return values().find { it.name.equals(value, ignoreCase = true) }
        }
    }
}




