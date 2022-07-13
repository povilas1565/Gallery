package com.example.Gallery.screens.addUsers

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.Gallery.models.User
import com.example.Gallery.R
import com.example.Gallery.screens.common.SimpleCallback
import com.example.Gallery.screens.common.loadUserPhoto
import kotlinx.android.synthetic.main.add_users_item.view.*

class UsersAdapter(private val listener: Listener) : RecyclerView.Adapter<UsersAdapter.ViewHolder>() {

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    interface Listener {
        fun follow(uid: String)
        fun unfollow(uid: String)
    }

    private var mUsers = listOf<User>()
    private var mPositions = mapOf<String, Int>()
    private var mFollows = mapOf<String, Boolean>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.add_users_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder.view) {
            val user = mUsers[position]
            photo_image.loadUserPhoto(user.photo)
            username_text.text = user.username
            name_text.text = user.name
            follow_btn.setOnClickListener { listener.follow(user.uid) }
            unfollow_btn.setOnClickListener { listener.unfollow(user.uid) }

            val follows = mFollows[user.uid] ?: false
            if (follows) {
                follow_btn.visibility = View.GONE
                unfollow_btn.visibility = View.VISIBLE
            } else {
                follow_btn.visibility = View.VISIBLE
                unfollow_btn.visibility = View.GONE
            }
        }
    }

    override fun getItemCount() = mUsers.size

    fun update(users: List<User>, follows: Map<String, Boolean>) {
        val diffResult = DiffUtil.calculateDiff(SimpleCallback(mUsers, users) { it.uid })
        mUsers = users
        mPositions = users.withIndex().map { (idx, user) -> user.uid to idx }.toMap()
        mFollows = follows
        diffResult.dispatchUpdatesTo(this)
    }

    fun followed(uid: String) {
        mFollows += (uid to true)
        notifyItemChanged(mPositions[uid]!!)
    }

    fun unfollowed(uid: String) {
        mFollows -= uid
        notifyItemChanged(mPositions[uid]!!)
    }
}
