package com.saber.users.db

import com.saber.users.data.User

class LocalDataSource(private val dao: UsersDao) {
    fun insert(items: List<User>) {
        dao.insertAll(items)
    }

    fun retrieveItems(): List<User> {
        return dao.getUsers()
    }

    fun getUserDetails(userID: Int): User {
        return dao.getUserDetails(userID)
    }
}