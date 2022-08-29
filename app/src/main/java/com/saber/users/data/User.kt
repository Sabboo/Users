package com.saber.users.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


@Entity
data class User(
    @PrimaryKey @SerializedName("id") val id: Int,
    @SerializedName("email") val email: String?,
    @SerializedName("first_name") val firstName: String?,
    @SerializedName("last_name") val lastName: String?,
    @SerializedName("avatar") val avatar: String?
) {
    fun getUserDetails() = "$id - $firstName $lastName"
}
