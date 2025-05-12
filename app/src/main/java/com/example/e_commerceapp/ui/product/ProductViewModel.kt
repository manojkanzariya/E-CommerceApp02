package com.example.e_commerceapp.ui.product

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.e_commerceapp.data.model.Product
import com.example.e_commerceapp.data.repository.ProductRepository

class ProductViewModel : ViewModel() {
    private val repository = ProductRepository()
    private val _productList = MutableLiveData<List<Product>>()
    val productList: LiveData<List<Product>> = _productList

    fun loadProducts() {
        repository.getProducts { products ->
            _productList.value = products
        }
    }

}
