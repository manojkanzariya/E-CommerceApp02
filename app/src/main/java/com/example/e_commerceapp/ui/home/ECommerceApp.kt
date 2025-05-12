package com.example.e_commerceapp.ui.home

import android.app.Application
import com.example.e_commerceapp.data.repository.CartManager
import com.example.e_commerceapp.data.repository.UserRepository

class ECommerceApp : Application() {
    override fun onCreate() {
        super.onCreate()
        CartManager.init(this)
        UserRepository.init(applicationContext)
    }
}