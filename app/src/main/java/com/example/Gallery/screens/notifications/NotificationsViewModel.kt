package com.example.Gallery.screens.notifications

import androidx.lifecycle.LiveData
import com.example.Gallery.models.Notification
import com.example.Gallery.data.NotificationsRepository
import com.example.Gallery.screens.common.MainViewModel
import com.google.android.gms.tasks.OnFailureListener

class NotificationsViewModel(private val notificationsRepo: NotificationsRepository,
                             onFailureListener: OnFailureListener) : MainViewModel(onFailureListener) {
    lateinit var notifications: LiveData<List<Notification>>
    private lateinit var uid: String

    fun init(uid: String) {
        if (!this::uid.isInitialized) {
            this.uid = uid
            notifications = notificationsRepo.getNotifications(uid)
        }
    }

    fun setNotificationsRead(notifications: List<Notification>) {
        val ids = notifications.filter { !it.read }.map { it.id }
        if (ids.isNotEmpty()) {
            notificationsRepo.setNotificationsRead(uid, ids, true)
                .addOnFailureListener(onFailureListener)
        }
    }
}