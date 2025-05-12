package com.example.e_commerceapp.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Category(
    val id: Int,
    val name: String,
    val iconRes: Int,
    val itemCount: Int = 0,

) : Parcelable
{
    constructor() : this(0, "", 0, 0)
}