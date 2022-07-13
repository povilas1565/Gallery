package com.example.Gallery.screens.profileSettings

import com.example.Gallery.common.AuthManager
import com.example.Gallery.screens.common.MainViewModel
import com.google.android.gms.tasks.OnFailureListener

class ProfileSettingsViewModel(private val authManager: AuthManager,
                               onFailureListener: OnFailureListener) : MainViewModel(onFailureListener), AuthManager by authManager
