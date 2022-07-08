package com.example.Gallery.Screens.Common

import android.content.Intent
import android.util.Log
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.Observer
import androidx.lifecycle.OnLifecycleEvent
import com.example.Gallery.Models.Notification
import com.example.Gallery.Models.NotificationType
import com.example.Gallery.R
import com.example.Gallery.Screens.Home.HomeActivity
import com.example.Gallery.Screens.Notifications.NotificationsActivity
import com.example.Gallery.Screens.Notifications.NotificationsViewModel
import com.example.Gallery.Screens.Profile.ProfileActivity
import com.example.Gallery.Screens.Search.SearchActivity
import com.example.Gallery.Screens.Share.ShareActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.database.core.view.View
import com.nhaarman.supertooltips.ToolTip
import com.nhaarman.supertooltips.ToolTipRelativeLayout
import com.nhaarman.supertooltips.ToolTipView
import kotlinx.android.synthetic.main.bottom_navigation_view.*

class GalleryBottomNavigation(
    private val uid: String,
    private val bnv: BottomNavigationView,
    private val tooltipLayout: ToolTipRelativeLayout,
    private val navNumber: Int,
    private val activity: MainActivity) : LifecycleObserver {

        private lateinit var mViewModel: NotificationsViewModel
        private lateinit var mNotificationsContentView: View
        private var lastTooltipView: ToolTipView? = null

        @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
        fun onCreate() {
            mViewModel = activity.initViewModel()
            mViewModel.init(uid)
            mNotificationsContentView = activity.layoutInflater.inflate(
                R.layout.notifications_tooltip_content, null, false
            )
            mViewModel.notifications.observe(activity, Observer {
                it?.let {
                    showNotifications(it)
                }
            })
        }

        private fun showNotifications(notifications: List<Notification>) {
            if (lastTooltipView != null) {
                val parent = mNotificationsContentView.parent
                if (parent != null) {
                    (parent as ViewGroup).removeView(mNotificationsContentView)
                    lastTooltipView?.remove()
                }
                lastTooltipView = null
            }

            val newNotifications = notifications.filter { !it.read }
            val newNotificationsMap = newNotifications
                .groupBy { it.type }
                .mapValues { (_, values) -> values.size }

            fun setCount(image: ImageView, textView: TextView, type: NotificationType) {
                val count = newNotificationsMap[type] ?: 0
                if (count == 0) {
                    image.visibility = View.GONE
                    textView.visibility = View.GONE
                } else {
                    image.visibility = View.VISIBLE
                    textView.visibility = View.VISIBLE
                    textView.text = count.toString()
                }
            }

            with(mNotificationsContentView) {
                setCount(likes_image, likes_count_text, NotificationType.Like)
                setCount(follows_image, follows_count_text, NotificationType.Follow)
                setCount(comments_image, comments_count_text, NotificationType.Comment)
            }

            if (newNotifications.isNotEmpty()) {
                bnv.getOrCreateBadge(R.id.nav_item_likes).number = newNotifications.size

                val tooltip = ToolTip()
                    .withColor(ContextCompat.getColor(activity, R.color.red))
                    .withContentView(mNotificationsContentView)
                    .withAnimationType(ToolTip.AnimationType.FROM_TOP)
                    .withShadow()
                lastTooltipView = tooltipLayout.showToolTipForView(
                    tooltip,
                    (bnv[0] as ViewGroup)[NOTIFICATIONS_ICON_POS]
                )
                lastTooltipView?.setOnToolTipViewClickedListener {
                    mViewModel.setNotificationsRead(newNotifications)
                    (bnv[0] as ViewGroup)[NOTIFICATIONS_ICON_POS].performClick()
                }
            }
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
        fun onResume() {
            bnv.menu.getItem(navNumber).isChecked = true
        }

    init {
        bnv.setOnNavigationItemSelectedListener {
            val nextActivity =
                when (it.itemId) {
                    R.id.nav_item_home -> HomeActivity::class.java
                    R.id.nav_item_search -> SearchActivity::class.java
                    R.id.nav_item_share -> ShareActivity::class.java
                    R.id.nav_item_likes -> NotificationsActivity::class.java
                    R.id.nav_item_profile -> ProfileActivity::class.java
                    else -> {
                        Log.e(MainActivity.TAG, "unknown nav item clicked $it")
                        null
                    }
                }
                if (nextActivity != null) {
                    val intent = Intent(activity, nextActivity)
                    intent.flags = Intent.FLAG_ACTIVITY_NO_ANIMATION
                    activity.startActivity(intent)
                    activity.overridePendingTransition(0, 0)
                    true
                } else {
                    false
                }
            }
        }

        companion object {
            const val NOTIFICATIONS_ICON_POS = 3
        }
    }

    fun MainActivity.setupBottomNavigation(uid: String, navNumber: Int) {
        val bnv =
            GalleryBottomNavigation(uid, bottom_navigation_view, tooltip_layout, navNumber, this)
        this.lifecycle.addObserver(bnv)
    }
