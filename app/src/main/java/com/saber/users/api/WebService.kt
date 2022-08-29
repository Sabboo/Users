package com.saber.users.api

import com.saber.users.data.UsersListResponseData
import com.saber.users.data.UserDetailsResponseData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface WebService {

    @GET("users?per_page=20")
    suspend fun getUsersDataResponse(): Response<UsersListResponseData>

    @GET("users/{userID}")
    suspend fun getUserDetailsResponse(@Path("userID") userID: Int): Response<UserDetailsResponseData>

}