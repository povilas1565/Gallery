package com.example.Gallery.screens.common

import androidx.lifecycle.ViewModel
import com.google.android.gms.tasks.OnFailureListener

abstract class MainViewModel(protected val onFailureListener: OnFailureListener) : ViewModel()
