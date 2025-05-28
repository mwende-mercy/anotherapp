package com.mwende.onlinestore

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.*
import com.google.firebase.FirebaseApp
import com.mwende.onlinestore.ui.*
import com.mwende.onlinestore.viewmodel.AuthViewModel
import com.mwende.onlinestore.viewmodel.ProductViewModel
import com.mwende.onlinestore.ui.theme.OnlineStoreTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)

        setContent {
            OnlineStoreTheme {
                val navController = rememberNavController()
                val productViewModel: ProductViewModel = viewModel()
                val authViewModel: AuthViewModel = viewModel()

                NavHost(navController, startDestination = "product_list") {
                    composable("product_list") {
                        ProductListScreen(
                            viewModel = productViewModel,
                            onAddProductClick = {
                                if (authViewModel.isAuthenticated.value) {
                                    navController.navigate("add_product")
                                } else {
                                    navController.navigate("login")
                                }
                            }
                        )
                    }
                    composable("add_product") {
                        AddProductScreen(
                            viewModel = productViewModel,
                            onProductAdded = { navController.popBackStack() }
                        )
                    }
                    composable("login") {
                        LoginScreen(
                            authViewModel = authViewModel,
                            onLoginSuccess = { navController.popBackStack() },
                            onNavigateToSignup = { navController.navigate("signup") }
                        )
                    }
                    composable("signup") {
                        SignupScreen(
                            authViewModel = authViewModel,
                            onSignupSuccess = { navController.popBackStack() },
                            onNavigateToLogin = { navController.popBackStack() }
                        )
                    }
                }
            }
        }
    }
}
