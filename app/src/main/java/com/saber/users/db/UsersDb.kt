package com.saber.users.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.saber.users.data.User


@Database(
    entities = [User::class],
    version = 1, exportSchema = false
)
abstract class UsersDb : RoomDatabase() {

    abstract fun usersDao(): UsersDao

}