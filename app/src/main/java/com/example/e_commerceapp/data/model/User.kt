package com.example.e_commerceapp.data.model

data class User(
    val address: String? = null,
    val name: String,
    val email: String,
    val profileImage: String? = null,
    val role: Any? = null
)
data class Address(
    val name: String,
    val phone: String,
    val address: String,
    val city: String,
    val pincode: String
)

