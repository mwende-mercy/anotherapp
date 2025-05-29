// com.mwende.onlinestore.ui.screens/HomeScreen.kt
package com.mwende.onlinestore.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.mwende.onlinestore.ui.components.ProductCard
import com.mwende.onlinestore.viewmodel.ProductViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: ProductViewModel = viewModel(),
    onLogout: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Online Store") },
                actions = {
                    // Shopping Cart Icon
                    IconButton(onClick = { navController.navigate("cart") }) {
                        Icon(
                            imageVector = Icons.Filled.ShoppingCart,
                            contentDescription = "Cart"
                        )
                    }
                    // Logout Icon
                    IconButton(onClick = onLogout) {
                        Icon(
                            imageVector = Icons.Filled.Logout,
                            contentDescription = "Logout"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(viewModel.products) { product ->
                ProductCard(
                    product = product,
                    onAddToCart = { viewModel.addToCart(product) }
                )
            }
        }
    }
}
