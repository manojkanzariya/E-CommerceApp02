package com.example.e_commerceapp.data.repository

import com.example.e_commerceapp.data.model.CartItem
import com.example.e_commerceapp.data.model.Product
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.tasks.await

object CartManager {
    private lateinit var sharedPreferences: SharedPreferences
    private const val CART_PREFS = "cart_prefs"
    private const val CART_KEY = "cart_items"
    private val gson = Gson()

    private val cartItems = mutableListOf<CartItem>()

    fun init(context: Context) {
        sharedPreferences = context.getSharedPreferences(CART_PREFS, Context.MODE_PRIVATE)
        loadCart()
    }

    suspend fun addToCart(product: Product) {
        val completeProduct = if (product.vendorId.isBlank()) {
            try {
                val snapshot = FirebaseFirestore.getInstance()
                    .collection("products")
                    .document(product.id)
                    .get()
                    .await() // Now available with coroutines

                product.copy(
                    vendorId = snapshot.getString("vendorId") ?: ""
                )
            } catch (e: Exception) {
                Log.e("Cart", "Failed to fetch vendorId", e)
                product // Return original if failed
            }
        } else {
            product
        }

        val existingItem = cartItems.find { it.product.id == completeProduct.id }
        if (existingItem != null) {
            existingItem.quantity++
        } else {
            cartItems.add(CartItem(completeProduct, 1))
        }
        saveCart()
    }

    fun removeFromCart(cartItem: CartItem) {
        cartItems.removeAll { it.product.id == cartItem.product.id }
        saveCart()
    }

    fun updateQuantity(cartItem: CartItem, newQuantity: Int) {
        val existingItem = cartItems.find { it.product.id == cartItem.product.id }
        if (existingItem != null) {
            if (newQuantity > 0) {
                existingItem.quantity = newQuantity
            } else {
                cartItems.remove(existingItem)
            }
        }
        saveCart()
    }

    fun getCartItems(): List<CartItem> = cartItems

    fun getCartSubtotal(): Double = cartItems.sumOf { it.totalPrice }

    // fun getCartTotal(): Double = getCartSubtotal()

    fun clearCart() {
        cartItems.clear()
        sharedPreferences.edit().remove(CART_KEY).apply()
    }

    private fun saveCart() {
        val json = gson.toJson(cartItems)
        sharedPreferences.edit().putString(CART_KEY, json).apply()
    }

    private fun loadCart() {
        val json = sharedPreferences.getString(CART_KEY, null)
        if (json != null) {
            val type = object : TypeToken<MutableList<CartItem>>() {}.type
            try {
                val loadedItems: List<CartItem> = gson.fromJson(json, type) ?: return
                cartItems.clear()
                cartItems.addAll(loadedItems.onEach { item ->
                    // Verify critical fields
                    if (item.product.vendorId.isBlank()) {
                        Log.w("CartLoad", "Fixing missing vendorId for ${item.product.id}")
                        fetchMissingVendorId(item.product)
                    }
                })
            } catch (e: Exception) {
                Log.e("CartLoad", "Error loading cart", e)
                clearCart() // Reset corrupt cart
            }
        }
    }

    private fun fetchMissingVendorId(product: Product) {
        FirebaseFirestore.getInstance()
            .collection("products")
            .document(product.id)
            .get()
            .addOnSuccessListener { doc ->
                doc.getString("vendorId")?.let { vendorId ->
                    product.vendorId = vendorId
                    saveCart() // Update saved cart
                }
            }
    }
}

