package com.example.e_commerceapp.data.repository

import android.util.Log
import com.example.e_commerceapp.data.model.DeliveryAddress
import com.example.e_commerceapp.data.model.Order
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.example.e_commerceapp.data.model.OrderItem
import com.example.e_commerceapp.data.model.OrderStatus
import com.google.firebase.firestore.Query
import com.example.e_commerceapp.data.model.Product
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FieldValue
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

object OrderRepository {

    fun placeOrder(cartItems: List<Product>, totalAmount: Double, paymentMethod: String, vendorId: String, onSuccess: (orderId: String, confirmationDetails: Order) -> Unit, onFailure: (Exception) -> Unit) {
        val user = FirebaseAuth.getInstance().currentUser ?: run {
            onFailure(Exception("User not logged in"))
            return
        }

        if (cartItems.isEmpty()) {
            onFailure(Exception("Cart is empty"))
            return
        }

        if (cartItems.any { it.vendorId != vendorId }) {
            onFailure(Exception("All products must be from the same vendor"))
            return
        }

        // Generate order Data
        val orderNumber = "EC${(10000000..99999999).random()}"

        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, 2)
        val minDate = SimpleDateFormat("MMM dd", Locale.getDefault()).format(calendar.time)
        calendar.add(Calendar.DAY_OF_YEAR, 1)
        val maxDate = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(calendar.time)
        val estimatedDelivery = "$minDate - $maxDate"

        val address = UserRepository.getAddress()?.let { addr ->
            "${addr.name}, ${addr.address}, ${addr.city} - ${addr.pincode}"
        } ?: "Address not specified"

        val deliveryAddress = DeliveryAddress(
            phone = address
        )

        val confirmationDetails = Order(
            orderId = orderNumber,
            date = System.currentTimeMillis(),
            total = totalAmount,
            deliveryAddress = deliveryAddress,
            estimatedDelivery = estimatedDelivery
        )
        val db = FirebaseFirestore.getInstance()

        val orderItems = cartItems.mapNotNull { product ->
            if (product.id.isBlank()) {
                null
            } else {
                OrderItem(
                    productId = product.id,
                    name = product.name,
                    quantity = product.quantity.takeIf { it > 0 } ?: 1,
                    price = product.price,
                    imageUrl = product.imageUrl
                )
            }
        }

        if (orderItems.isEmpty()) {
            onFailure(Exception("No valid products in cart"))
            return
        }

        val order = hashMapOf(
            "userId" to user.uid,
            "orderId" to orderNumber,
            "vendorId" to vendorId,
            "date" to System.currentTimeMillis(),
            "total" to totalAmount,
            "timestamp" to FieldValue.serverTimestamp(),
            "items" to orderItems.map { it.toMap() },
            "paymentMethod" to paymentMethod,
            "paymentStatus" to if (paymentMethod == "Cash on Delivery") "Pending" else "Paid",
            "deliveryAddress" to address,
            "estimatedDelivery" to estimatedDelivery,
            "status" to OrderStatus.PROCESSING.name
        )

        db.collection("orders")
            .add(order)
            .addOnSuccessListener { documentReference ->
                Log.d("OrderRepository", "Order placed successfully. ID: ${documentReference.id}")
                onSuccess(documentReference.id, confirmationDetails)
            }
            .addOnFailureListener { e ->
                Log.e("OrderRepository", "Order placement failed", e)
                onFailure(e)
            }
    }

    fun fetchOrders(onResult: (List<Order>) -> Unit) {
        val uid = FirebaseAuth.getInstance().currentUser?.uid ?: return

        FirebaseFirestore.getInstance()
            .collection("orders")
            .whereEqualTo("userId", uid)
            .orderBy("date", Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener { snapshot ->
                val orders = snapshot.documents.mapNotNull { doc ->
                    try {
                        val deliveryAddressField = doc.get("deliveryAddress")
                        val deliveryAddress = when (deliveryAddressField) {
                            is Map<*, *> -> {
                                DeliveryAddress(
                                    name = deliveryAddressField["name"] as? String ?: "",
                                    phone = deliveryAddressField["phone"] as? String ?: "",
                                    address = deliveryAddressField["address"] as? String ?: "",
                                    city = deliveryAddressField["city"] as? String ?: "",
                                    pincode = deliveryAddressField["pincode"] as? String ?: ""
                                )
                            }
                            is String -> {
                                DeliveryAddress(
                                    address = deliveryAddressField
                                )
                            }
                            else -> {
                                DeliveryAddress()
                            }
                        }
                        val itemsList = (doc.get("items") as? List<Map<String, Any>>)?.map { itemMap ->
                            OrderItem(
                                productId = itemMap["productId"] as? String ?: "",
                                name = itemMap["name"] as? String ?: "",
                                price = (itemMap["price"] as? Number)?.toDouble() ?: 0.0,
                                imageUrl = itemMap["imageUrl"] as? String ?: "",
                                quantity = (itemMap["quantity"] as? Number)?.toInt() ?: 1
                            )
                        } ?: emptyList()

                        val order = Order(
                            id = doc.id,
                            orderId = doc.getString("orderId") ?: "",
                            userId = doc.getString("userId") ?: "",
                            vendorId = doc.getString("vendorId") ?: "",
                            productId = try {
                                val items = doc.get("items") as? List<Map<String, Any>>
                                items?.firstOrNull()?.get("productId") as? String ?: ""
                            } catch (e: Exception) {
                                ""
                            },
                            quantity = try {
                                val items = doc.get("items") as? List<Map<String, Any>>
                                (items?.firstOrNull()?.get("quantity") as? Long)?.toInt() ?: 1
                            } catch (e: Exception) {
                                1
                            },
                            date = doc.getLong("date") ?: 0L,
                            total = doc.getDouble("total") ?: 0.0,
                            timestamp = doc.getTimestamp("timestamp") ?: Timestamp.now(),
                            paymentMethod = doc.getString("paymentMethod") ?: "",
                            paymentStatus = doc.getString("paymentStatus") ?: "",
                            deliveryAddress = deliveryAddress,
                            estimatedDelivery = doc.getString("estimatedDelivery") ?: "",
                            status = OrderStatus.fromString(doc.getString("status") ?: "") ?: OrderStatus.PENDING,
                            items = itemsList
                        )

                        order
                    } catch (e: Exception) {
                        Log.e("OrderFetch", "Error parsing order ${doc.id}", e)
                        null
                    }
                }

                onResult(orders)
            }
            .addOnFailureListener { e ->
                Log.e("OrderRepository", "Failed to fetch orders", e)
                onResult(emptyList())
            }
    }


}


