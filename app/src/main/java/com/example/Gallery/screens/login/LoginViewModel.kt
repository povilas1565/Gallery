
package com.example.Gallery.screens.login

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.Gallery.R
import com.example.Gallery.common.AuthManager
import com.example.Gallery.common.SingleLiveEvent
import com.example.Gallery.screens.common.CommonViewModel
import com.example.Gallery.screens.common.MainViewModel
import com.google.android.gms.tasks.OnFailureListener


class LoginViewModel(private val authManager: AuthManager,
                     private val app: Application,
                     private val commonViewModel: CommonViewModel,
                     onFailureListener: OnFailureListener) : MainViewModel(onFailureListener) {
    private val _goToHomeScreen = SingleLiveEvent<Unit>()
    val goToHomeScreen: LiveData<Unit> = _goToHomeScreen
    private val _goToRegisterScreen = SingleLiveEvent<Unit>()
    val goToRegisterScreen: LiveData<Unit> = _goToRegisterScreen

    fun onLoginClick(email: String, password: String) {
        if (validate(email, password)) {
            authManager.signIn(email, password).addOnSuccessListener {
                _goToHomeScreen.value = Unit
            }.addOnFailureListener(onFailureListener)
        } else {
            commonViewModel.setErrorMessage(app.getString(R.string.please_enter_email_and_password))
        }
    }

    private fun validate(email: String, password: String) =
        email.isNotEmpty() && password.isNotEmpty()

    fun onRegisterClick() {
        _goToRegisterScreen.call()
    }
}







