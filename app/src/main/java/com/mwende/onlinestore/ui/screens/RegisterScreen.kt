package com.mwende.onlinestore.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.mwende.onlinestore.model.AuthState
import com.mwende.onlinestore.viewmodel.AuthViewModel

@Composable
fun RegisterScreen(
    navController: NavController,
    authViewModel: AuthViewModel = viewModel(),
    onNavigateToStore: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    LaunchedEffect(authViewModel.authState) {
        if (authViewModel.authState.value is AuthState.Authenticated) {
            onNavigateToStore()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(24.dp))
        Button(
            onClick = { authViewModel.signUp(email, password) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Register")
        }
        Spacer(modifier = Modifier.height(8.dp))
        TextButton(
            onClick = { navController.popBackStack() }
        ) {
            Text("Already have an account? Login")
        }
    }
}

