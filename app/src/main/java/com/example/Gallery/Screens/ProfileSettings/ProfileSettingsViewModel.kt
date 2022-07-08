package com.example.Gallery.Screens.ProfileSettings

import com.example.Gallery.Common.AuthManager
import com.example.Gallery.Screens.Common.MainViewModel
import com.google.android.gms.tasks.OnFailureListener

class ProfileSettingsViewModel(private val authManager: AuthManager,
                               onFailureListener: OnFailureListener) : MainViewModel(onFailureListener), AuthManager by authManager
