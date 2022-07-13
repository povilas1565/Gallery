package com.example.Gallery.common

import com.google.android.gms.tasks.Task

interface AuthManager {
    fun signOut()
    fun signIn(Email: String, password: String): Task<Unit>
}