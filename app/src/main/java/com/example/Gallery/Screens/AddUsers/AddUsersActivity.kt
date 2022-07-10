package com.example.Gallery.Screens.AddUsers

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.Gallery.Models.User
import com.example.Gallery.R
import com.example.Gallery.Screens.Common.MainActivity
import com.example.Gallery.Screens.Common.setupAuthGuard
import kotlinx.android.synthetic.main.activity_add_users.*


class AddUsersActivity :  MainActivity(), UsersAdapter.Listener {
    private lateinit var mUser: User
    private lateinit var mAdapter: UsersAdapter
    private lateinit var mViewModel: AddUsersViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_users)

        mAdapter = UsersAdapter(this)

        setupAuthGuard {
            mViewModel = initViewModel()

            back_image.setOnClickListener { finish() }

            add_users_recycler.adapter = mAdapter
            add_users_recycler.layoutManager = LinearLayoutManager(this)

            mViewModel.userAndUsers.observe(this, Observer {
                it?.let { (user, otherUsers) ->
                    mUser = user
                    mAdapter.update(otherUsers, mUser.follows)
                }
            })
        }
    }

    override fun follow(uid: String) {
        setFollow(uid, true) {
            mAdapter.followed(uid)
        }
    }

    override fun unfollow(uid: String) {
        setFollow(uid, false) {
            mAdapter.unfollowed(uid)
        }
    }

    private fun setFollow(uid: String, follow: Boolean, onSuccess: () -> Unit) {
        mViewModel.setFollow(mUser.uid, uid, follow)
            .addOnSuccessListener { onSuccess() }
    }
}
