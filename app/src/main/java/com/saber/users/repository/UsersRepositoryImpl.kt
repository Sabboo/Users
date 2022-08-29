package com.saber.users.repository

import androidx.annotation.WorkerThread
import com.saber.users.api.RemoteDataSource
import com.saber.users.data.User
import com.saber.users.data.Result
import com.saber.users.db.LocalDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import javax.inject.Inject


class UsersRepositoryImpl @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource,
    private val ioDispatcher: CoroutineDispatcher
) : UsersRepository {

    @WorkerThread
    override fun getUsersFlow(): Flow<Result<List<User>>> {
        return flow {
            val cacheResult = fetchCachedUsers()
            if (cacheResult.status == Result.Status.SUCCESS)
                emit(cacheResult)
            else {
                emit(Result.loading())
                val remoteResult = fetchRemoteUsers()
                if (remoteResult.status == Result.Status.SUCCESS)
                    emit(Result.success(localDataSource.retrieveItems()))
                else
                    emit(remoteResult)
            }
        }.flowOn(ioDispatcher)
    }

    private fun fetchCachedUsers(): Result<List<User>> {
        val result = localDataSource.retrieveItems()
        return if (result.isEmpty().not())
            Result.success(result)
        else
            Result.error("No Cache", null)
    }

    private suspend fun fetchRemoteUsers(): Result<List<User>> {
        val remoteResult = remoteDataSource.fetchUsers()

        if (remoteResult.status == Result.Status.SUCCESS) {
            remoteResult.data?.let { it ->
                localDataSource.insert(it)
            }
        }
        return remoteResult
    }

    override fun getUserDetailsFlow(userID: Int): Flow<Result<User>> {
        return flow {
            val cacheResult = fetchCachedUser(userID)
            if (cacheResult.status == Result.Status.SUCCESS)
                emit(cacheResult)
            else {
                emit(Result.loading())
                val remoteResult = fetchRemoteUser(userID)
                if (remoteResult.status == Result.Status.SUCCESS)
                    emit(Result.success(localDataSource.getUserDetails(userID)))
                else
                    emit(remoteResult)
            }
        }.flowOn(ioDispatcher)
    }

    private fun fetchCachedUser(userID: Int): Result<User> {
        val result = localDataSource.getUserDetails(userID)
        return Result.success(result)
    }

    private suspend fun fetchRemoteUser(userID: Int): Result<User> {
        val remoteResult = remoteDataSource.fetchUserDetails(userID)

        if (remoteResult.status == Result.Status.SUCCESS) {
            remoteResult.data?.let { it ->
                localDataSource.insert(listOf(it))
            }
        }
        return remoteResult
    }

}
