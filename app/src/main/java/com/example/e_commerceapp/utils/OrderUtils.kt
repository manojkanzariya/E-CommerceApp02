package com.example.e_commerceapp.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object OrderUtils {
    fun formatDate(timestamp: Long): String {
        return SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(Date(timestamp))
    }
}
