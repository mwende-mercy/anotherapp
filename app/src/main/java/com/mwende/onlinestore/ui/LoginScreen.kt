package com.mwende.onlinestore.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mwende.onlinestore.viewmodel.AuthViewModel

@Composable
fun LoginScreen(
    authViewModel: AuthViewModel,
    onLoginSuccess: () -> Unit,
    onNavigateToSignup: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var error by remember { mutableStateOf<String?>(null) }

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Login", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(value = email, onValueChange = { email = it }, label = { Text("Email") })
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(value = password, onValueChange = { password = it }, label = { Text("Password") })
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            if (email.isNotBlank() && password.isNotBlank()) {
                authViewModel.login(email, password) { success, errorMsg ->
                    if (success) {
                        onLoginSuccess()
                    } else {
                        error = errorMsg
                    }
                }
            }
        }) {
            Text("Login")
        }
        TextButton(onClick = onNavigateToSignup) {
            Text("Don't have an account? Sign up")
        }
        error?.let { Text(it, color = MaterialTheme.colorScheme.error) }
    }
}
