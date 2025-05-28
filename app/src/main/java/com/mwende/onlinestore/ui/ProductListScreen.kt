package com.mwende.onlinestore.ui

//package com.mwende.onlinestore.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.mwende.onlinestore.viewmodel.ProductViewModel
import com.mwende.onlinestore.data.Product

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductListScreen(viewModel: ProductViewModel, onAddProductClick: () -> Unit) {
    val products = viewModel.products.collectAsState().value

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Product List") })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddProductClick) {
                Text("+")
            }
        }
    ) { paddingValues ->
        LazyColumn(
            contentPadding = paddingValues,
            modifier = Modifier.padding(16.dp)
        ) {
            items(products) { product ->
                ProductItem(product)
            }
        }
    }
}

@Composable
fun ProductItem(product: Product) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(product.name, style = MaterialTheme.typography.titleMedium)
            Text(product.description, style = MaterialTheme.typography.bodyMedium)
            Text("Price: $${product.price}", style = MaterialTheme.typography.bodyLarge)
            Image(
                painter = rememberImagePainter(data = product.imageUrl),
                contentDescription = null,
                modifier = Modifier
                    .height(150.dp)
                    .fillMaxWidth(),
                contentScale = ContentScale.Crop
            )
        }
    }
}
