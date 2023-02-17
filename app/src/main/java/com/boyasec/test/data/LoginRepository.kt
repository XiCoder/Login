package com.boyasec.test.data

import com.boyasec.test.User
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class LoginRepository {

    suspend fun login(account: String, password: String): Flow<Result<User>> {
        return flow {
            delay(1500)
            emit(Result.Success(User(account, password)))
        }
    }
}