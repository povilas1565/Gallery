package com.example.Gallery.screens.profileSettings

import android.os.Bundle
import com.example.Gallery.R
import com.example.Gallery.screens.common.MainActivity
import com.example.Gallery.screens.common.setupAuthGuard
import kotlinx.android.synthetic.main.activity_profile_settings.*


class ProfileSettingsActivity : MainActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_settings)

        setupAuthGuard {
            val viewModel = initViewModel<ProfileSettingsViewModel>()
            sign_out_text.setOnClickListener { viewModel.signOut() }
            back_image.setOnClickListener { finish() }
        }
    }
}
