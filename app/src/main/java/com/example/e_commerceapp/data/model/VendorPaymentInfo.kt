package com.example.e_commerceapp.data.model

data class VendorPaymentInfo(
    val upiId: String,
    val upiEnabled: Boolean,
    val accountNumber: String,
    val bankName: String,
    val accountHolderName: String,
    val ifscCode: String,
    val bankTransferEnabled: Boolean
)