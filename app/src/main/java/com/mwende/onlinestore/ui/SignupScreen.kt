package com.mwende.onlinestore.ui
//package com.mwende.onlinestore.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mwende.onlinestore.viewmodel.AuthViewModel

@Composable
fun SignupScreen(
    authViewModel: AuthViewModel,
    onSignupSuccess: () -> Unit,
    onNavigateToLogin: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var error by remember { mutableStateOf<String?>(null) }

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Sign Up", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(value = email, onValueChange = { email = it }, label = { Text("Email") })
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(value = password, onValueChange = { password = it }, label = { Text("Password") })
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(value = confirmPassword, onValueChange = { confirmPassword = it }, label = { Text("Confirm Password") })
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            if (email.isNotBlank() && password.isNotBlank() && confirmPassword.isNotBlank()) {
                if (password != confirmPassword) {
                    error = "Passwords do not match"
                } else {
                    authViewModel.signup(email, password) { success, errorMsg ->
                        if (success) {
                            onSignupSuccess()
                        } else {
                            error = errorMsg
                        }
                    }
                }
            }
        }) {
            Text("Sign Up")
        }
        TextButton(onClick = onNavigateToLogin) {
            Text("Already have an account? Login")
        }
        error?.let { Text(it, color = MaterialTheme.colorScheme.error) }
    }
}
