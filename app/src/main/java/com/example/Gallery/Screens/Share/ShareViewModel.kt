package com.example.Gallery.Screens.Share

import android.net.Uri
import com.example.Gallery.Common.SingleLiveEvent
import com.example.Gallery.Models.FeedPost
import com.example.Gallery.Models.User
import com.example.Gallery.Data.FeedPostsRepository
import com.example.Gallery.Data.UsersRepository
import com.example.Gallery.Screens.Common.MainViewModel
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.Tasks

class ShareViewModel(private val feedPostsRepo: FeedPostsRepository,
                     private val usersRepo: UsersRepository,
                     onFailureListener: OnFailureListener) : MainViewModel(onFailureListener) {
    private val _shareCompletedEvent = SingleLiveEvent<Unit>()
    val shareCompletedEvent = _shareCompletedEvent
    val user = usersRepo.getUser()

    fun share(user: User, imageUri: Uri?, caption: String) {
        if (imageUri != null) {
            usersRepo.uploadUserImage(user.uid, imageUri).onSuccessTask { downloadUrl ->
                Tasks.whenAll(
                    usersRepo.setUserImage(user.uid, downloadUrl!!),
                    feedPostsRepo.createFeedPost(user.uid, mkFeedPost(user, caption,
                        downloadUrl.toString()))
                )
            }.addOnCompleteListener{
                _shareCompletedEvent.call()
            }.addOnFailureListener(onFailureListener)
        }
    }

    private fun mkFeedPost(user: User, caption: String, imageDownloadUrl: String): FeedPost {
        return FeedPost(
            uid = user.uid,
            username = user.username,
            image = imageDownloadUrl,
            caption = caption,
            photo = user.photo
        )
    }
}
