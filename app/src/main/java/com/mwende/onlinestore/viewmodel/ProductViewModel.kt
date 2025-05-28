package com.mwende.onlinestore.viewmodel

import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.mwende.onlinestore.data.Product
//import com.mwende.onlinestore.model.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ProductViewModel : ViewModel() {

    private val db = FirebaseFirestore.getInstance()
    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> = _products

    init {
        loadProducts()
    }

    private fun loadProducts() {
        db.collection("products")
            .addSnapshotListener { snapshot, _ ->
                if (snapshot != null) {
                    val productList = snapshot.documents.mapNotNull { it.toObject(Product::class.java) }
                    _products.value = productList
                }
            }
    }

    fun addProduct(product: Product, onResult: (Boolean, String?) -> Unit) {
        db.collection("products")
            .add(product)
            .addOnSuccessListener { onResult(true, null) }
            .addOnFailureListener { e -> onResult(false, e.message) }
    }
}
