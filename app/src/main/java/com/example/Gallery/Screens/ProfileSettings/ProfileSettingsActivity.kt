package com.example.Gallery.Screens.ProfileSettings

import android.os.Bundle
import com.example.Gallery.R
import com.example.Gallery.Screens.Common.MainActivity
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
