package com.mwende.onlinestore

// com.mwende.onlinestore.OnlineStoreApplication
//package com.mwende.onlinestore

import android.app.Application
import com.google.firebase.FirebaseApp

class OnlineStoreApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
    }
}