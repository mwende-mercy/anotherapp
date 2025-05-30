// com.mwende.onlinestore.model/AuthState.kt
package com.mwende.onlinestore.model

sealed class AuthState {
    object Loading : AuthState()  // Proper object declaration
    data class Authenticated(val userId: String) : AuthState()
    data class Unauthenticated(val error: String? = null) : AuthState()
}

