package com.example.school_system.data.network

import com.example.school_system.data.model.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    @POST("users")
    suspend fun createUser(@Body user: User): User

    @GET("users")
    suspend fun getUsers(): List<User>
}
