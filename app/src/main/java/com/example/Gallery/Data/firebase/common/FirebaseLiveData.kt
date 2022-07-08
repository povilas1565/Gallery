package com.example.Gallery.Data.firebase.common

import androidx.lifecycle.LiveData
import com.example.Gallery.Common.ValueEventListenerAdapter
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.Query

class FirebaseLiveData(private val query: Query) : LiveData<DataSnapshot>() {
    private val listener = ValueEventListenerAdapter {
        value = it
    }

    override fun onActive() {
        super.onActive()
        query.addValueEventListener(listener)
    }

    override fun onInactive() {
        super.onInactive()
        query.removeEventListener(listener)
    }
}
