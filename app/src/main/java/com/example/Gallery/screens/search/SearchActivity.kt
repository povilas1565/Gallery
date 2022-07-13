@file:Suppress("DEPRECATION", "RedundantSamConstructor")

package com.example.Gallery.screens.search

import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.Gallery.R
import com.example.Gallery.screens.common.ImagesAdapter
import com.example.Gallery.screens.common.MainActivity
import com.example.Gallery.screens.common.setupAuthGuard
import com.example.Gallery.screens.common.setupBottomNavigation
import kotlinx.android.synthetic.main.activity_search.*


class SearchActivity : MainActivity(), TextWatcher {
private lateinit var mAdapter: ImagesAdapter
    private lateinit var mViewModel: SearchViewModel
    private var isSearchEntered = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        Log.d(TAG, "onCreate")

        setupAuthGuard {uid ->
            setupBottomNavigation(uid,1)
            mAdapter = ImagesAdapter()
            search_results_recycler.layoutManager = GridLayoutManager(this, 3)
            search_results_recycler.adapter = mAdapter

            mViewModel = initViewModel()
            mViewModel.posts.observe(this, Observer {it?.let{ posts ->
                mAdapter.updateImages(posts.map {it.image })
            }})

            search_input.addTextChangedListener(this)
            mViewModel.setSearchText("")
        }
    }

    override fun afterTextChanged(s: Editable?) {
        if (!isSearchEntered) {
            isSearchEntered = true
            Handler().postDelayed({
                isSearchEntered = false
                mViewModel.setSearchText(search_input.text.toString())
            }, 500)
        }
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { }

    companion object {
        const val TAG = "SearchActivity"
    }
}
