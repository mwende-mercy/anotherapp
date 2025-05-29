package com.mwende.onlinestore.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.mwende.onlinestore.model.AuthState
import com.mwende.onlinestore.model.UserData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class AuthViewModel : ViewModel() {
    private val auth: FirebaseAuth = Firebase.auth

    private val _authState = MutableStateFlow<AuthState>(AuthState.Unauthenticated())
    val authState: StateFlow<AuthState> = _authState

    private val _userData = MutableStateFlow<UserData?>(null)
    val userData: StateFlow<UserData?> = _userData

    init {
        checkCurrentUser()
    }

    private fun checkCurrentUser() {
        _authState.value = AuthState.Loading()
        if (auth.currentUser != null) {
            _authState.value = AuthState.Authenticated(auth.currentUser!!.uid)
            _userData.value = UserData(
                userId = auth.currentUser!!.uid,
                email = auth.currentUser!!.email
            )
        } else {
            _authState.value = AuthState.Unauthenticated()
        }
    }

    fun signIn(email: String, password: String) {
        viewModelScope.launch {
            try {
                _authState.value = AuthState.Loading()
                auth.signInWithEmailAndPassword(email, password).await()
                checkCurrentUser()
            } catch (e: Exception) {
                _authState.value = AuthState.Unauthenticated(e.message)
            }
        }
    }

    fun signUp(email: String, password: String) {
        viewModelScope.launch {
            try {
                _authState.value = AuthState.Loading()
                auth.createUserWithEmailAndPassword(email, password).await()
                checkCurrentUser()
            } catch (e: Exception) {
                _authState.value = AuthState.Unauthenticated(e.message)
            }
        }
    }

    fun signOut() {
        auth.signOut()
        _authState.value = AuthState.Unauthenticated()
        _userData.value = null
    }
}

