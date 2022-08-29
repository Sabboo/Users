package com.saber.users.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.saber.users.data.User

@Dao
interface UsersDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(users: List<User>)

    @Query("SELECT * FROM User")
    fun getUsers(): List<User>

    @Query("SELECT * FROM User WHERE id == :userID")
    fun getUserDetails(userID: Int): User
}
