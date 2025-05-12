package com.example.e_commerceapp.data.model

import java.util.Date

data class Review(
    val id: String = "",
    val userId: String = "",
    val userName: String = "",
    val productId: String = "",
    val rating: Float = 0f,
    val comment: String = "",
    val date: String = "",
    val timestamp: Date = Date()
)