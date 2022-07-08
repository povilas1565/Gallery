package com.example.Gallery.Data


import androidx.lifecycle.LiveData
import com.example.Gallery.Models.Notification
import com.google.android.gms.tasks.Task

interface NotificationsRepository {
    fun createNotification(uid: String, notification: Notification): Task<Unit>
    fun getNotifications(uid: String): LiveData<List<Notification>>
    fun setNotificationsRead(uid: String, ids: List<String>, read: Boolean): Task<Unit>
}
