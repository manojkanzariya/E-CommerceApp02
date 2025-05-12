package com.example.e_commerceapp.data.repository

import android.content.Context
import android.content.SharedPreferences
import com.example.e_commerceapp.data.model.Address
import com.example.e_commerceapp.data.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

object UserRepository {
    private var user: User? = null
    private var sharedPreferences: SharedPreferences? = null
    private var savedAddress: Address? = null

    suspend fun fetchUserFromFirebase(uid: String): User? {
        return try {
            val document = FirebaseFirestore.getInstance()
                .collection("users")
                .document(uid)
                .get()
                .await()

            if (document.exists()) {
                User(
                    name = document.getString("name") ?: "",
                    email = document.getString("email") ?: "",
                    profileImage = document.getString("profileImage"),
                    address = document.getString("address")
                ).also { user ->
                    // Save to local storage
                    setUser(user)
                }
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }

    // Update getUser to try fetching from Firebase if local data is missing
    suspend fun getUser(): User? {
        if (user == null) {
            val uid = FirebaseAuth.getInstance().currentUser?.uid ?: return null
            return fetchUserFromFirebase(uid)
        }
        return user
    }

    // Initialize Repository
    fun init(context: Context) {
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
            loadUser()
            loadSavedAddress()
        }
    }

    // Save Address Persistently
    fun saveAddress(name: String, phone: String, address: String, city: String, pincode: String) {
        savedAddress = Address(name, phone, address, city, pincode)

        sharedPreferences?.edit()?.apply {
            putString("address_name", name)
            putString("address_phone", phone)
            putString("address_line", address)
            putString("address_city", city)
            putString("address_pincode", pincode)
            apply()
        }
    }

    // Check if Address Exists
    fun hasAddress(): Boolean {
        return getAddress() != null
    }

    // Load Address from SharedPreferences
    private fun loadSavedAddress() {
        sharedPreferences?.let { prefs ->
            val name = prefs.getString("address_name", null) ?: return
            val phone = prefs.getString("address_phone", null) ?: return
            val address = prefs.getString("address_line", null) ?: return
            val city = prefs.getString("address_city", null) ?: return
            val pincode = prefs.getString("address_pincode", null) ?: return
            savedAddress = Address(name, phone, address, city, pincode)
        }
    }

    // Get Saved Address
    fun getAddress(): Address? {
        if (savedAddress == null) {
            loadSavedAddress()
        }
        return savedAddress
    }

    // Load User Data from SharedPreferences
    private fun loadUser() {
        sharedPreferences?.let { prefs ->
            if (prefs.getBoolean("is_logged_in", false)) {
                user = User(
                    name = prefs.getString("user_name", "") ?: "",
                    email = prefs.getString("user_email", "") ?: "",
                    profileImage = prefs.getString("user_image", null),
                    address = prefs.getString("user_address", null)
                )
            }
        }
    }

    // Save User Data
    fun setUser(newUser: User) {
        user = newUser
        sharedPreferences?.edit()?.apply {
            putBoolean("is_logged_in", true)
            putString("user_name", newUser.name)
            putString("user_email", newUser.email)
            newUser.profileImage?.let { putString("user_image", it) }
            apply()
        }
    }
}

