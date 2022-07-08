package com.example.Gallery.Screens.Common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.Gallery.Screens.AddUsers.AddUsersViewModel
import com.example.Gallery.Screens.Comments.CommentsViewModel
import com.example.Gallery.Screens.EditProfile.EditProfileViewModel
import com.example.Gallery.Screens.Register.RegisterViewModel
import com.example.Gallery.Screens.GalleryApp
import com.example.Gallery.Screens.Home.HomeViewModel
import com.example.Gallery.Screens.Login.LoginViewModel
import com.example.Gallery.Screens.Notifications.NotificationsViewModel
import com.example.Gallery.Screens.Profile.ProfileViewModel
import com.example.Gallery.Screens.ProfileSettings.ProfileSettingsViewModel
import com.example.Gallery.Screens.Search.SearchViewModel
import com.example.Gallery.Screens.Share.ShareViewModel
import com.google.android.gms.tasks.OnFailureListener

@Suppress("UNCHECKED_CAST")
class ViewModelFactory(private val app: GalleryApp,
                       private val commonViewModel: CommonViewModel,
                       private val onFailureListener: OnFailureListener) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        val usersRepo = app.usersRepo
        val feedPostsRepo = app.feedPostsRepo
        val authManager = app.authManager
        val notificationsRepo = app.notificationsRepo
        val searchRepo = app.searchRepo

        if (modelClass.isAssignableFrom(AddUsersViewModel::class.java)) {
            return AddUsersViewModel(onFailureListener, usersRepo, feedPostsRepo) as T
        } else if (modelClass.isAssignableFrom(EditProfileViewModel::class.java)) {
            return EditProfileViewModel(onFailureListener, usersRepo) as T
        } else if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(onFailureListener, feedPostsRepo) as T
        } else if (modelClass.isAssignableFrom(ProfileSettingsViewModel::class.java)) {
            return ProfileSettingsViewModel(authManager, onFailureListener) as T
        } else if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(authManager, app, commonViewModel, onFailureListener) as T
        } else if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
            return ProfileViewModel(usersRepo, onFailureListener) as T
        } else if (modelClass.isAssignableFrom(RegisterViewModel::class.java)) {
            return RegisterViewModel(commonViewModel, app, onFailureListener, usersRepo) as T
        } else if (modelClass.isAssignableFrom(ShareViewModel::class.java)) {
            return ShareViewModel(feedPostsRepo, usersRepo, onFailureListener) as T
        } else if (modelClass.isAssignableFrom(CommentsViewModel::class.java)) {
            return CommentsViewModel(feedPostsRepo, usersRepo, onFailureListener) as T
        } else if (modelClass.isAssignableFrom(NotificationsViewModel::class.java)) {
            return NotificationsViewModel(notificationsRepo, onFailureListener) as T
        } else if (modelClass.isAssignableFrom(SearchViewModel::class.java)) {
            return SearchViewModel(searchRepo, onFailureListener) as T
        } else {
            error("Unknown view model class $modelClass")
        }
    }
}