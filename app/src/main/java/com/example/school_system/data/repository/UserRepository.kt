package com.example.school_system.data.repository

import com.example.school_system.data.model.User
import com.example.school_system.data.network.ApiClient

class UserRepository {

    suspend fun createUser(user: User): User {
        return ApiClient.api.createUser(user)
    }

    suspend fun getUsers(): List<User> {
        return ApiClient.api.getUsers()
    }
}
