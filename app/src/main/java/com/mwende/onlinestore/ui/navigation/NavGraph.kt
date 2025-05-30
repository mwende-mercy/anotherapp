// com.mwende.onlinestore.ui.navigation/NavGraph.kt
package com.mwende.onlinestore.ui.navigation

import android.app.Application
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mwende.onlinestore.ui.screens.CartScreen
import com.mwende.onlinestore.ui.screens.HomeScreen
import com.mwende.onlinestore.ui.screens.LoginScreen
import com.mwende.onlinestore.ui.screens.RegisterScreen
import com.mwende.onlinestore.viewmodel.AuthViewModel
import com.mwende.onlinestore.viewmodel.AuthViewModelFactory
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mwende.onlinestore.model.AuthState

@Composable
fun AppNavHost() {
    val navController = rememberNavController()
    val context = LocalContext.current
    val authViewModel: AuthViewModel = viewModel(
        factory = AuthViewModelFactory(context.applicationContext as Application)
    )
    val authState by authViewModel.authState.collectAsStateWithLifecycle()

    // Handle auth state changes
    LaunchedEffect(authState) {
        when (authState) {
            is AuthState.Authenticated -> {
                if (navController.currentDestination?.route != "home") {
                    navController.navigate("home") {
                        popUpTo("login") { inclusive = true }
                        launchSingleTop = true
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
        startDestination = "login"
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
                onLogout = {
                    authViewModel.signOut()
                    navController.navigate("login") { popUpTo(0) }
                }
            )
        }
        composable("cart") {
            CartScreen(navController = navController)
        }
    }
}