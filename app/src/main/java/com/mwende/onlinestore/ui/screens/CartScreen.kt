// com.mwende.onlinestore.ui.screens/CartScreen.kt
package com.mwende.onlinestore.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.mwende.onlinestore.ui.components.CartItemCard
import com.mwende.onlinestore.viewmodel.ProductViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(
    navController: NavController,
    viewModel: ProductViewModel = viewModel(),
    onBackClick: () -> Unit = { navController.popBackStack() } // Default back behavior
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Your Cart") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) { // Uses the passed callback
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            LazyColumn(modifier = Modifier.weight(1f)) {
                items(viewModel.cartItems.value) { item ->
                    CartItemCard(
                        item = item,
                        onIncreaseQuantity = { viewModel.addToCart(item.product) },
                        onDecreaseQuantity = { viewModel.removeFromCart(item.product) }
                    )
                }
            }
            // ... (rest of your CartScreen code)
        }
    }
}
