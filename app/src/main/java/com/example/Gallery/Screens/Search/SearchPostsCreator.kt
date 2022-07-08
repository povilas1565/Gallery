package com.example.Gallery.Screens.Search

import android.util.Log
import androidx.lifecycle.Observer
import com.example.Gallery.Common.BaseEventListener
import com.example.Gallery.Common.Event
import com.example.Gallery.Common.EventBus
import com.example.Gallery.Models.SearchPost
import com.example.Gallery.Data.SearchRepository

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