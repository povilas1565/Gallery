package com.example.Gallery.Common.firebase

import com.example.Gallery.Common.AuthManager
import com.example.Gallery.Common.toUnit
import com.example.Gallery.Data.firebase.common.auth
import com.google.android.gms.tasks.Task

class FirebaseAuthManager : AuthManager {
    override fun signOut() {
        auth.signOut()
    }

    override fun signIn(Email: String, password: String): Task<Unit> =
        auth.signInWithEmailndPassword(Email, password).toUnit()
}



