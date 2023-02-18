package com.boyasec.test.viewmodels

import androidx.lifecycle.ViewModel
import com.boyasec.test.data.User
import com.boyasec.test.data.LoginRepository
import com.boyasec.test.data.Result
import kotlinx.coroutines.flow.Flow

/**
 * LoginViewModel
 * @author Hey
 */
class LoginViewModel(private val loginRepository: LoginRepository) : ViewModel() {

    suspend fun login(account: String, password: String): Flow<Result<User>> {
        return loginRepository.login(account, password)
    }

}