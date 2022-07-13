package com.example.Gallery.screens

import android.app.Application
import com.example.Gallery.common.firebase.FirebaseAuthManager
import com.example.Gallery.data.firebase.FirebaseFeedPostsRepository
import com.example.Gallery.data.firebase.FirebaseNotificationsRepository
import com.example.Gallery.data.firebase.FirebaseSearchRepository
import com.example.Gallery.data.firebase.FirebaseUsersRepository
import com.example.Gallery.screens.notifications.NotificationsCreator
import com.example.Gallery.screens.search.SearchPostsCreator

class GalleryApp: Application() {
    val usersRepo by lazy { FirebaseUsersRepository() }
    val feedPostsRepo by lazy { FirebaseFeedPostsRepository() }
    val notificationsRepo by lazy { FirebaseNotificationsRepository() }
    val authManager by lazy { FirebaseAuthManager() }
    val searchRepo by lazy { FirebaseSearchRepository() }

    override fun onCreate() {
        super.onCreate()
        NotificationsCreator(notificationsRepo, usersRepo, feedPostsRepo)
        SearchPostsCreator(searchRepo)
    }
}