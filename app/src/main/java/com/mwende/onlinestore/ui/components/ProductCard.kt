package com.mwende.onlinestore.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.mwende.onlinestore.model.Product

@Composable
fun ProductCard(
    product: Product,
    onAddToCart: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Image(
                painter = rememberAsyncImagePainter(product.imageUrl),
                contentDescription = product.name,
                modifier = Modifier.height(120.dp)
            )
            Text(text = product.name, style = MaterialTheme.typography.titleLarge)
            Text(text = "$${product.price}", style = MaterialTheme.typography.bodyLarge)
            Button(
                onClick = onAddToCart,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Add to Cart")
            }
        }
    }
}

