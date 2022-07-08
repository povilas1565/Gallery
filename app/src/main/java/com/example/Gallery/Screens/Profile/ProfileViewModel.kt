package com.example.Gallery.Screens.Profile

import androidx.lifecycle.LiveData
import com.example.Gallery.Data.UsersRepository
import com.example.Gallery.Screens.Common.MainViewModel
import com.google.android.gms.tasks.OnFailureListener

class ProfileViewModel(private val usersRepo: UsersRepository, onFailureListener: OnFailureListener) : MainViewModel(onFailureListener) {
    val user = usersRepo.getUser()
    lateinit var images: LiveData<List<String>>

    fun init(uid: String) {
        if (!this::images.isInitialized) {
            images = usersRepo.getImages(uid)
        }
    }
}
