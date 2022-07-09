package com.example.Gallery.Screens.AddFriends

import androidx.lifecycle.LiveData
import com.example.Gallery.Models.User
import com.example.Gallery.Data.FeedPostsRepository
import com.example.Gallery.Data.UsersRepository
import com.example.Gallery.Data.common.map
import com.example.Gallery.Screens.Common.MainViewModel
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks

class AddFriendsViewModel(onFailureListener: OnFailureListener,
                        private val usersRepo: UsersRepository,
                        private val feedPostsRepo: FeedPostsRepository) : MainViewModel(onFailureListener) {
    val userAndFriends: LiveData<Pair<User, List<User>>> =
        usersRepo.getUsers().map { allUsers ->
            val (userList, otherUsersList) = allUsers.partition {
                it.uid == usersRepo.currentUid()
            }
            userList.first() to otherUsersList
        }

    fun setFollow(currentUid: String, uid: String, follow: Boolean): Task<Void> {
        return (if (follow) {
            Tasks.whenAll(
                usersRepo.addFollow(currentUid, uid),
                usersRepo.addFollower(currentUid, uid),
                feedPostsRepo.copyFeedPosts(postsAuthorUid = uid, uid = currentUid))
        } else {
            Tasks.whenAll(
                usersRepo.deleteFollow(currentUid, uid),
                usersRepo.deleteFollower(currentUid, uid),
                feedPostsRepo.deleteFeedPosts(postsAuthorUid = uid, uid = currentUid))
        }).addOnFailureListener(onFailureListener)
    }
}