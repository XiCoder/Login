package com.boyasec.test.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.boyasec.test.data.LoginRepository

/**
 * LoginViewModel工厂类
 * @author Hey
 */
class LoginViewModelFactory : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(loginRepository = LoginRepository()) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}