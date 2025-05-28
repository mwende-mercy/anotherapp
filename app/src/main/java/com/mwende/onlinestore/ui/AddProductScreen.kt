package com.mwende.onlinestore.ui

///package com.mwende.onlinestore.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mwende.onlinestore.data.Product
import com.mwende.onlinestore.viewmodel.ProductViewModel

@Composable
fun AddProductScreen(
    viewModel: ProductViewModel,
    onProductAdded: () -> Unit
) {
    var name by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var error by remember { mutableStateOf<String?>(null) }

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Add Product", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Product Name") })
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(value = price, onValueChange = { price = it }, label = { Text("Price") })
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(value = description, onValueChange = { description = it }, label = { Text("Description") })
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            if (name.isNotBlank() && price.toDoubleOrNull() != null) {
                val newProduct = Product(
                    id = 0, // temporary id, or replace with actual id if available
                    name = name,
                    description = description,
                    price = price.toDouble(),
                    imageUrl = "" // provide actual image URL here or leave empty
                )
                viewModel.addProduct(newProduct) { success, errorMsg ->
                    if (success) {
                        onProductAdded()
                    } else {
                        error = errorMsg
                    }
                }
            }
            else {
                error = "Please enter valid details"
            }
        }) {
            Text("Add Product")
        }
        error?.let { Text(it, color = MaterialTheme.colorScheme.error) }
    }
}
