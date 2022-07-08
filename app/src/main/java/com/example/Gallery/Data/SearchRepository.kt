package com.example.Gallery.Data

import androidx.lifecycle.LiveData
import com.example.Gallery.Models.SearchPost
import com.google.android.gms.tasks.Task

interface SearchRepository {
fun searchPosts(text: String): LiveData<List<SearchPost>>
fun createPost(post: SearchPost): Task<Unit>
}

