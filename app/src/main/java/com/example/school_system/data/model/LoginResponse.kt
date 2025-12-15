package com.example.school_system.data.model
import com.example.school_system.data.model.UserData
data class LoginResponse(
    val success: Boolean,
    val message: String,
    val user: UserData?
)