package com.mwende.onlinestore.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mwende.onlinestore.model.CartItem
import com.mwende.onlinestore.model.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProductViewModel : ViewModel() {
    // Sample products
    private val _products = listOf(
        Product(1, "Smartphone", 599.99, "https://via.placeholder.com/150"),
        Product(2, "Laptop", 1299.99, "https://via.placeholder.com/150"),
        Product(3, "Headphones", 199.99, "https://via.placeholder.com/150")
    )
    val products: List<Product> = _products

    // Cart state
    private val _cartItems = MutableStateFlow<List<CartItem>>(emptyList())
    val cartItems: StateFlow<List<CartItem>> = _cartItems

    fun addToCart(product: Product) {
        viewModelScope.launch {
            val currentCart = _cartItems.value.toMutableList()
            val existingItem = currentCart.find { it.product.id == product.id }

            existingItem?.let {
                it.quantity++
            } ?: run {
                currentCart.add(CartItem(product))
            }
            _cartItems.value = currentCart
        }
    }

    fun removeFromCart(product: Product) {
        viewModelScope.launch {
            val currentCart = _cartItems.value.toMutableList()
            currentCart.removeAll { it.product.id == product.id }
            _cartItems.value = currentCart
        }
    }
}
