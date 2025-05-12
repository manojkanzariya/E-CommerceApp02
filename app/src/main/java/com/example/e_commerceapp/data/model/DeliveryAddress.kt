package com.example.e_commerceapp.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DeliveryAddress(
    var name: String = "",
    var phone: String = "",
    var address: String = "",
    var city: String = "",
    var pincode: String = ""
) : Parcelable

