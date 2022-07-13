@file:Suppress("RedundantSamConstructor")

package com.example.Gallery.screens.notifications

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.Gallery.R
import com.example.Gallery.screens.common.MainActivity
import com.example.Gallery.screens.common.setupAuthGuard
import com.example.Gallery.screens.common.setupBottomNavigation
import kotlinx.android.synthetic.main.activity_notifications.*


class NotificationsActivity : MainActivity() {
    private lateinit var mAdapter: NotificationsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notifications)
        Log.d(TAG, "onCreate")

        setupAuthGuard { uid ->
            setupBottomNavigation(uid,3)
            mAdapter = NotificationsAdapter()
            notifications_recycler.layoutManager = LinearLayoutManager(this)
            notifications_recycler.adapter = mAdapter

            val viewModel = initViewModel<NotificationsViewModel>()
            viewModel.init(uid)
            viewModel.notifications.observe(this, Observer {
                it?.let {
                    mAdapter.updateNotifications(it)
                    viewModel.setNotificationsRead(it)
                }
            })
        }
    }

    companion object {
        const val TAG = "LikesActivity"
    }
}
