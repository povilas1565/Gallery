@file:Suppress("DEPRECATION", "RedundantSamConstructor")

package com.example.Gallery.screens.share

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import com.example.Gallery.data.firebase.common.FirebaseHelper
import com.example.Gallery.models.User
import com.example.Gallery.R
import com.example.Gallery.screens.common.CameraHelper
import com.example.Gallery.screens.common.MainActivity
import com.example.Gallery.screens.common.loadImage
import com.example.Gallery.screens.common.setupAuthGuard
import kotlinx.android.synthetic.main.activity_share.*


class ShareActivity : MainActivity() {
    private lateinit var mCamera: CameraHelper
    private lateinit var mFirebase: FirebaseHelper
    private lateinit var mUser: User
    private lateinit var mViewModel: ShareViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_share)
        Log.d(TAG, "onCreate")

        setupAuthGuard {
            mViewModel = initViewModel()
            mFirebase = FirebaseHelper(this)

            mCamera = CameraHelper(this)
            mCamera.takeCameraPicture()

            back_image.setOnClickListener { finish() }
            share_text.setOnClickListener { share() }

            mViewModel.user.observe(this, Observer {
                it?.let {
                    mUser = it
                }
            })
            mViewModel.shareCompletedEvent.observe(this, Observer {
                finish()
            })
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == mCamera.REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                post_image.loadImage(mCamera.imageUri?.toString())
            } else {
                finish()
            }
        }
    }

    private fun share() {
        mViewModel.share(mUser, mCamera.imageUri, caption_input.text.toString())
    }

    companion object {
        const val TAG = "ShareActivity"
    }
}
