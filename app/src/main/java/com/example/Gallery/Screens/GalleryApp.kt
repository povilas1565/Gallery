package com.example.Gallery.Screens

import android.app.Application
import com.example.Gallery.Common.firebase.FirebaseAuthManager
import com.example.Gallery.Data.firebase.FirebaseFeedPostsRepository
import com.example.Gallery.Data.firebase.FirebaseNotificationsRepository
import com.example.Gallery.Data.firebase.FirebaseSearchRepository
import com.example.Gallery.Data.firebase.FirebaseUsersRepository
import com.example.Gallery.Screens.Notifications.NotificationsCreator
import com.example.Gallery.Screens.Search.SearchPostsCreator

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