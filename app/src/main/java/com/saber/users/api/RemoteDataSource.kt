package com.saber.users.api

import com.saber.users.data.UsersListResponseData
import com.saber.users.data.User
import com.saber.users.data.Result
import com.saber.users.data.UserDetailsResponseData
import com.saber.users.utils.ErrorUtils
import retrofit2.Retrofit
import java.net.UnknownHostException
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val retrofit: Retrofit) {

    private val defaultErrorMessage = "Error fetching remote data"

    suspend fun fetchUsers(): Result<List<User>> {
        val webService = retrofit.create(WebService::class.java)
        return try {
            val result = webService.getUsersDataResponse()
            if (result.isSuccessful) {
                val responseBody = result.body() as UsersListResponseData
                Result.success(responseBody.usersList)
            } else {
                val errorResponse = ErrorUtils.parseError(result, retrofit)
                Result.error(
                    errorResponse?.message ?: defaultErrorMessage, errorResponse
                )
            }
        } catch (e: UnknownHostException) {
            Result.error("No Connection", null)
        } catch (e: Throwable) {
            Result.error("Unknown Error", null)
        }
    }

    suspend fun fetchUserDetails(userID: Int): Result<User> {
        val webService = retrofit.create(WebService::class.java)
        return try {
            val result = webService.getUserDetailsResponse(userID)
            if (result.isSuccessful) {
                val responseBody = result.body() as UserDetailsResponseData
                return Result.success(responseBody.userDetails)
            } else {
                val errorResponse = ErrorUtils.parseError(result, retrofit)
                Result.error(
                    errorResponse?.message ?: defaultErrorMessage, errorResponse
                )
            }
        } catch (e: UnknownHostException) {
            Result.error("No Connection", null)
        } catch (e: Throwable) {
            Result.error("Unknown Error", null)
        }
    }

}