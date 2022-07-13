package com.example.Gallery.screens.search

import android.util.Log
import androidx.lifecycle.Observer
import com.example.Gallery.common.BaseEventListener
import com.example.Gallery.common.Event
import com.example.Gallery.common.EventBus
import com.example.Gallery.models.SearchPost
import com.example.Gallery.data.SearchRepository

class SearchPostsCreator(searchRepo: SearchRepository) : BaseEventListener() {
    init {
        EventBus.events.observe(this, Observer {
            it?.let { event ->
                when (event) {
                    is Event.CreateFeedPost -> {
                        val searchPost = with(event.post) {
                            SearchPost(
                                image = image,
                                caption = caption,
                                postId = id)
                        }
                        searchRepo.createPost(searchPost).addOnFailureListener {
                            Log.d(TAG, "Failed to create search post for event: $event", it)
                        }
                    }
                    else -> {
                    }
                }
            }
        })
    }

    companion object {
        const val TAG = "SearchPostsCreator"
    }
}