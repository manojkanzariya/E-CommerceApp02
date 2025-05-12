package com.example.e_commerceapp.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey

class PreferenceHelper(context: Context) {
    private val preferences = context.getSharedPreferences("vendor_prefs", Context.MODE_PRIVATE)
    private val PREF_PHONE_NUMBER = "phone_number"

    private val masterKey = MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    private val sharedPreferences: SharedPreferences = EncryptedSharedPreferences.create(
        context,
        "vendor_prefs",
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    // Store Information
    fun setStoreName(name: String) = sharedPreferences.edit().putString("store_name", name).apply()
    fun getStoreName(): String? = sharedPreferences.getString("store_name", null)

    // Email Preferences
    fun setEmailPreference(key: String, enabled: Boolean) =
        sharedPreferences.edit().putBoolean("email_$key", enabled).apply()

    fun getEmailPreference(key: String): Boolean =
        sharedPreferences.getBoolean("email_$key", true) // Default to true

    fun setPaymentMethodEnabled(method: String, enabled: Boolean) {
        preferences.edit().putBoolean("payment_$method", enabled).apply()
    }

    fun isPaymentMethodEnabled(method: String): Boolean {
        return preferences.getBoolean("payment_$method", false)
    }

    // Clear all preferences
    fun clearPreferences() = sharedPreferences.edit().clear().apply()

    fun setPhoneNumber(phone: String) {
        preferences.edit().putString(PREF_PHONE_NUMBER, phone).apply()
    }

    fun getPhoneNumber(): String? {
        return preferences.getString(PREF_PHONE_NUMBER, null)
    }

    fun setStoreDescription(desc: String) {
        preferences.edit().putString("store_description", desc).apply()
    }

    fun getStoreDescription(): String? {
        return preferences.getString("store_description", null)
    }

    fun setStoreContact(contact: String) {
        preferences.edit().putString("store_contact", contact).apply()
    }

    fun getStoreContact(): String? {
        return preferences.getString("store_contact", null)
    }
    fun setShippingDeliveryOption(enabled: Boolean) {
        preferences.edit().putBoolean("shipping_delivery", enabled).apply()
    }

    fun getShippingDeliveryOption(): Boolean {
        return preferences.getBoolean("shipping_delivery", true)
    }

    fun setShippingPickupOption(enabled: Boolean) {
        preferences.edit().putBoolean("shipping_pickup", enabled).apply()
    }

    fun getShippingPickupOption(): Boolean {
        return preferences.getBoolean("shipping_pickup", false)
    }

    fun setShippingFlatRate(rate: Double) {
        preferences.edit().putFloat("shipping_flat_rate", rate.toFloat()).apply()
    }

    fun getShippingFlatRate(): Double? {
        return preferences.getFloat("shipping_flat_rate", 0.0f).toDouble()
    }

    fun setFreeShippingThreshold(threshold: Double) {
        preferences.edit().putFloat("free_shipping_threshold", threshold.toFloat()).apply()
    }

    fun getFreeShippingThreshold(): Double? {
        return preferences.getFloat("free_shipping_threshold", 0.0f).toDouble()
    }


}