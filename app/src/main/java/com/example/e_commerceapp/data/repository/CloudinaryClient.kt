package com.example.e_commerceapp.data.repository

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object CloudinaryClient {
    private const val BASE_URL = "https://api.cloudinary.com/v1_1/dk0xbsufx/"

    val service: CloudinaryService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CloudinaryService::class.java)
    }
}
