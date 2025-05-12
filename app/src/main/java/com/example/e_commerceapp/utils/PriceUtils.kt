package com.example.e_commerceapp.utils

object PriceUtils {
    // Formats price with ₹ symbol and 2 decimal places
    fun formatPrice(price: Double): String {
        return "₹${"%.2f".format(price)}"
    }

    // Extension function for Double to format with any currency symbol
    fun Double.formatWithSymbol(currencySymbol: String = "₹"): String {
        return "$currencySymbol${"%.2f".format(this)}"
    }

    // Additional helper function for Indian number formatting (comma separators)
    fun formatPriceWithCommas(price: Double): String {
        return "₹${"%,.2f".format(price)}"
    }
}