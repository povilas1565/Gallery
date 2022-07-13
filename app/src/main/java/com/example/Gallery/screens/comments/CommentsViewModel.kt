package com.example.Gallery.screens.comments

import androidx.lifecycle.LiveData
import com.example.Gallery.models.Comment
import com.example.Gallery.models.User
import com.example.Gallery.data.FeedPostsRepository
import com.example.Gallery.data.UsersRepository
import com.example.Gallery.screens.common.MainViewModel
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
