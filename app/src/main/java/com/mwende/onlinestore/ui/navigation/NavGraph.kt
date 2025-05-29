// com.mwende.onlinestore.ui.navigation/NavGraph.kt
package com.mwende.onlinestore.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mwende.onlinestore.model.AuthState
import com.mwende.onlinestore.ui.screens.CartScreen
import com.mwende.onlinestore.ui.screens.HomeScreen
import com.mwende.onlinestore.ui.screens.LoginScreen
import com.mwende.onlinestore.ui.screens.RegisterScreen
import com.mwende.onlinestore.viewmodel.AuthViewModel

@Composable
fun AppNavHost() {
    val navController = rememberNavController()
    val authViewModel: AuthViewModel = viewModel()
    val authState by authViewModel.authState.collectAsStateWithLifecycle()

    // Automatically navigate based on auth state
    LaunchedEffect(authState) {
        when (authState) {
            is AuthState.Authenticated -> {
                if (navController.currentDestination?.route !in listOf("home", "cart")) {
                    navController.navigate("home") {
                        popUpTo("login") { inclusive = true }
                    }
                }
            }
            is AuthState.Unauthenticated -> {
                if (navController.currentDestination?.route != "login") {
                    navController.navigate("login") {
                        popUpTo(0)
                    }
                }
            }
            else -> {}
        }
    }

    NavHost(
        navController = navController,
        startDestination = "login" // Default to login, will auto-redirect if authenticated
    ) {
        composable("login") {
            LoginScreen(
                navController = navController,
                onNavigateToStore = { navController.navigate("home") }
            )
        }

        composable("register") {
            RegisterScreen(
                navController = navController,
                onNavigateToStore = { navController.navigate("home") }
            )
        }

        composable("home") {
            HomeScreen(
                navController = navController,
                onLogout = { authViewModel.signOut() }
            )
        }

        composable("cart") {
            CartScreen(
                navController = navController,
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}
