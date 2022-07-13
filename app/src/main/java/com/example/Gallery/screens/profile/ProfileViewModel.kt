package com.example.Gallery.screens.profile

import androidx.lifecycle.LiveData
import com.example.Gallery.data.UsersRepository
import com.example.Gallery.screens.common.MainViewModel
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
