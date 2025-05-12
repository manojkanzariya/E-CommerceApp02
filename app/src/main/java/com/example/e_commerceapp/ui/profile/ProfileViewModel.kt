package com.example.e_commerceapp.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_commerceapp.data.model.Order
import com.example.e_commerceapp.data.model.User
import com.example.e_commerceapp.data.repository.OrderRepository
import com.example.e_commerceapp.data.repository.UserRepository
import kotlinx.coroutines.launch

class ProfileViewModel : ViewModel() {
    private val repository = UserRepository
    private val _userData = MutableLiveData<User?>()
    private val _orderHistoryLiveData = MutableLiveData<List<Order>>()
    private val _isLoading = MutableLiveData<Boolean>()

    val userData: LiveData<User?> = _userData
    val orderHistoryLiveData: LiveData<List<Order>> = _orderHistoryLiveData

    fun loadUserData() {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                _userData.value = repository.getUser() // This now calls the suspend function
            } catch (e: Exception) {
                // Handle error
                _userData.value = null
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun loadOrderHistory() {
        OrderRepository.fetchOrders { orders ->
            _orderHistoryLiveData.postValue(orders) // or update adapter if directly in fragment
        }

    }
}
