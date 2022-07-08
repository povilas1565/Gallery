package com.example.Gallery.Screens.Comments

import androidx.lifecycle.LiveData
import com.example.Gallery.Models.Comment
import com.example.Gallery.Models.User
import com.example.Gallery.Data.FeedPostsRepository
import com.example.Gallery.Data.UsersRepository
import com.example.Gallery.Screens.Common.MainViewModel
import com.google.android.gms.tasks.OnFailureListener

class CommentsViewModel(private val feedPostsRepo: FeedPostsRepository,
                        usersRepo: UsersRepository,
                        onFailureListener: OnFailureListener) : MainViewModel(onFailureListener) {
    lateinit var comments: LiveData<List<Comment>>
    private lateinit var postId: String
    val user: LiveData<User> = usersRepo.getUser()

    fun init(postId: String) {
        this.postId = postId
        comments = feedPostsRepo.getComments(postId)
    }

    fun createComment(text: String, user: User) {
        val comment = Comment(
            uid = user.uid,
            username = user.username,
            photo = user.photo,
            text = text)
        feedPostsRepo.createComment(postId, comment).addOnFailureListener(onFailureListener)
    }
}
