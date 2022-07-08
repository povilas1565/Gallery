package com.example.Gallery.Models

import com.google.firebase.database.Exclude

data class SearchPost(val image: String = "", val caption: String = "", val postId: String = "",
                      @get:Exclude val id: String = "")
