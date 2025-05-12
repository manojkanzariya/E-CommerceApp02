package com.example.e_commerceapp.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_commerceapp.data.model.Product
import com.example.e_commerceapp.data.repository.WishlistRepository
import kotlinx.coroutines.launch

class WishlistViewModel : ViewModel() {
    private val repository = WishlistRepository()
    private val _wishlistItems = MutableLiveData<List<Product>>()
    private val _isLoading = MutableLiveData<Boolean>()
    private val _errorMessage = MutableLiveData<String?>()

    val wishlistItems: LiveData<List<Product>> = _wishlistItems
    val isLoading: LiveData<Boolean> = _isLoading
    val errorMessage: LiveData<String?> = _errorMessage

    fun loadWishlist() {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val products = repository.getWishlistProducts()
                _wishlistItems.value = products
                _errorMessage.value = null
            } catch (e: Exception) {
                _errorMessage.value = "Failed to load wishlist: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun removeFromWishlist(productId: String) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                repository.removeFromWishlist(productId)
                // Refresh the list after removal
                loadWishlist()
            } catch (e: Exception) {
                _errorMessage.value = "Failed to remove item: ${e.message}"
                _isLoading.value = false
            }
        }
    }
}