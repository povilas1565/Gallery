package com.example.Gallery.Screens.Notifications

import android.util.Log
import androidx.lifecycle.Observer
import com.example.Gallery.Common.BaseEventListener
import com.example.Gallery.Common.Event
import com.example.Gallery.Common.EventBus
import com.example.Gallery.Models.Notification
import com.example.Gallery.Models.NotificationType
import com.example.Gallery.Data.FeedPostsRepository
import com.example.Gallery.Data.NotificationsRepository
import com.example.Gallery.Data.UsersRepository
import com.example.Gallery.Data.common.observeFirstNotNull
import com.example.Gallery.Data.common.zip

class NotificationsCreator(private val notificationsRepo: NotificationsRepository,
                            private val usersRepo: UsersRepository,
                            private val feedPostsRepo: FeedPostsRepository) : BaseEventListener() {
    init {
        EventBus.events.observe(this, Observer {
            it?.let { event ->
                when (event) {
                    is Event.CreateFollow -> {
                        getUser(event.fromUid).observeFirstNotNull(this) { user ->
                            val notification = Notification(
                                uid = user.uid,
                                username = user.username,
                                photo = user.photo,
                                type = NotificationType.Follow)
                            notificationsRepo.createNotification(event.toUid, notification)
                                .addOnFailureListener {
                                    Log.d(TAG, "Failed to create notification", it)
                                }
                        }
                    }
                    is Event.CreateLike -> {
                        val userData = usersRepo.getUser(event.uid)
                        val postData = feedPostsRepo.getFeedPost(uid = event.uid, postId = event.postId)

                        userData.zip(postData).observeFirstNotNull(this) { (user, post) ->
                            val notification = Notification(
                                uid = user.uid,
                                username = user.username,
                                photo = user.photo,
                                postId = post.id,
                                postImage = post.image,
                                type = NotificationType.Like)
                            notificationsRepo.createNotification(post.uid, notification)
                                .addOnFailureListener {
                                    Log.d(TAG, "Failed to create notification", it)
                                }
                        }
                    }
                    is Event.CreateComment -> {
                        feedPostsRepo.getFeedPost(uid = event.comment.uid, postId = event.postId)
                            .observeFirstNotNull(this) { post ->
                                val notification = Notification(
                                    uid = event.comment.uid,
                                    username = event.comment.username,
                                    photo = event.comment.photo,
                                    postId = event.postId,
                                    postImage = post.image,
                                    commentText = event.comment.text,
                                    type = NotificationType.Comment)
                                notificationsRepo.createNotification(post.uid, notification)
                                    .addOnFailureListener {
                                        Log.d(TAG, "Failed to create notification", it)
                                    }
                            }
                    }
                }
            }
        })
    }

    private fun getUser(uid: String) = usersRepo.getUser(uid)

    companion object {
        const val TAG = "NotificationsCreator"
    }
}
