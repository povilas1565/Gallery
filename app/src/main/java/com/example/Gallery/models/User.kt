@file:Suppress("RemoveEmptyClassBody")

package com.example.Gallery.models

import com.google.firebase.database.Exclude

data class User(val name: String = "", val username: String = "", val email: String = "",
val follows: Map<String, Boolean> = emptyMap(),
val followers: Map<String, Boolean> = emptyMap(),
                val bio: String? = null, val phone: Long? = null, val country: String? = null,
val photo: String? = null, @Exclude val uid: String = "") {
}