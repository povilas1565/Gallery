
package com.example.Gallery.common.firebase



import com.example.Gallery.common.AuthManager
import com.example.Gallery.common.toUnit
import com.example.Gallery.data.firebase.common.auth
import com.google.android.gms.tasks.Task

class FirebaseAuthManager : AuthManager {
    override fun signOut() {
        auth.signOut()
    }

override fun signIn(email: String, password: String): Task<Unit> =
    auth.signInWithEmailAndPassword(email, password).toUnit()
}


































