package com.mwende.onlinestore.model

sealed class AuthState {
    object Loading : AuthState()
    data class Authenticated(val userId: String) : AuthState()
    data class Unauthenticated(val error: String? = null) : AuthState()
}

