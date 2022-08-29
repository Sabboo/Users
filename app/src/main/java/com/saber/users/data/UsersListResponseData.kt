package com.saber.users.data

import com.google.gson.annotations.SerializedName

data class UsersListResponseData (
    @SerializedName("data") val usersList: List<User>?
)
