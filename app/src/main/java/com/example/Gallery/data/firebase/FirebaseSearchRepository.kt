@file:Suppress("DEPRECATION", "NestedLambdaShadowedImplicitParameter")

package com.example.Gallery.data.firebase

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import com.example.Gallery.common.toUnit
import com.example.Gallery.models.SearchPost
import com.example.Gallery.data.SearchRepository
import com.example.Gallery.data.common.map
import com.example.Gallery.data.firebase.common.FirebaseLiveData
import com.example.Gallery.data.firebase.common.database
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot

class FirebaseSearchRepository: SearchRepository {
    @SuppressLint("DefaultLocale")
    override fun createPost(post: SearchPost): Task<Unit> =
        database.child("search-posts").push()
            .setValue(post.copy(caption = post.caption.toLowerCase())).toUnit()

    @SuppressLint("DefaultLocale")
    override fun searchPosts(text: String): LiveData<List<SearchPost>> {
        val reference = database.child("search-posts")
        val query = if (text.isEmpty()) {
            reference
        } else {
            reference.orderByChild("caption")
                .startAt(text.toLowerCase()).endAt("${text.toLowerCase()}\\uf8ff")
        }
        return FirebaseLiveData(query).map {
            it.children.map { it.asSearchPost()!! }
        }
    }
}

private fun DataSnapshot.asSearchPost(): SearchPost? =
    getValue(SearchPost::class.java)?.copy(id = key!!)
