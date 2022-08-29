package com.saber.users.repository

import com.saber.users.data.User
import com.saber.users.data.Result
import kotlinx.coroutines.flow.Flow

interface UsersRepository {
    fun getUsersFlow(): Flow<Result<List<User>>>
    fun getUserDetailsFlow(userID: Int): Flow<Result<User>>
}