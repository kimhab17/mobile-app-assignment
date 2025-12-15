package com.example.school_system.data.network

import com.example.school_system.data.model.LoginResponse
import com.example.school_system.data.model.UserData
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part

interface ApiService {

    @POST("/register")
    suspend fun registerUser(@Body user: UserData): UserData

    @POST("/login")
    suspend fun loginUser(@Body credentials: Map<String, String>): LoginResponse

    @POST("/profile")
    suspend fun getProfile(@Body body: Map<String, String>): LoginResponse

    @PUT("/update-profile")
    suspend fun updateProfile(@Body user: UserData): LoginResponse

    // ✅ Profile Photo Upload
    @Multipart
    @POST("/upload-profile")
    suspend fun uploadProfileImage(
        @Part file: MultipartBody.Part,
        @Part("email") email: RequestBody
    ): LoginResponse
}
