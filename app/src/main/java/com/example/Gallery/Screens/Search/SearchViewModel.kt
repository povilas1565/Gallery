package com.example.Gallery.Screens.Search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.Gallery.Models.SearchPost
import com.example.Gallery.Data.SearchRepository
import com.example.Gallery.Screens.Common.MainViewModel
import com.google.android.gms.tasks.OnFailureListener

class SearchViewModel(searchRepo: SearchRepository,
                      onFailureListener: OnFailureListener) : MainViewModel(onFailureListener) {
    private val searchText = MutableLiveData<String>()

    val posts: LiveData<List<SearchPost>> = Transformations.switchMap(searchText) { text ->
        searchRepo.searchPosts(text)
    }

    fun setSearchText(text: String) {
        searchText.value = text
    }
}

