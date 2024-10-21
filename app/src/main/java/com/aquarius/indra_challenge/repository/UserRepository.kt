package com.aquarius.indra_challenge.repository

import com.aquarius.indra_challenge.data.UserDao
import com.aquarius.indra_challenge.data.UserEntity

class UserRepository(private val userDao: UserDao) {
    suspend fun getUser(username: String, password: String): UserEntity? {
        return userDao.getUser(username, password)
    }

    suspend fun insertUser(user: UserEntity) {
        userDao.insertUser(user)
    }
}
