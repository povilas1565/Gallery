package com.example.Gallery.Screens.Common

import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.Gallery.Screens.GalleryApp
import com.example.Gallery.Screens.Login.LoginActivity

class MainActivity : AppCompatActivity() {
    lateinit var commonViewModel: CommonViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        commonViewModel = ViewModelProviders.of(this).get(CommonViewModel::class.java)
        commonViewModel.errorMessage.observe(this, Observer {
            it?.let {
                showToast(it)
            }
        })
    }

    inline fun <reified T : MainViewModel> initViewModel(): T =
        ViewModelProviders.of(this, ViewModelFactory(
            application as GalleryApp,
            commonViewModel,
            commonViewModel)).get(T::class.java)

    fun goToLogin() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

    companion object {
        const val TAG = "MainActivity"
    }
}
