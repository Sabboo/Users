package com.saber.users.data

import com.google.gson.annotations.SerializedName

data class UserDetailsResponseData(
    @SerializedName("data") val userDetails: User?
)
