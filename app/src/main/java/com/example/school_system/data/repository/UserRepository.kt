package com.example.school_system.data.repository

import com.example.school_system.data.model.LoginResponse
import com.example.school_system.data.model.UserData
import com.example.school_system.data.network.ApiClient
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

class UserRepository {

    // ✅ Register user
    suspend fun registerUser(user: UserData): UserData {
        return try {
            ApiClient.api.registerUser(user)
        } catch (e: Exception) {
            throw Throwable("Register បរាជ័យ: ${e.message}")
        }
    }

    // get profile teacher
suspend fun getProfile(email: String): LoginResponse {
    return try {
        ApiClient.api.getProfile(mapOf("email" to email))
    } catch (e: Exception) {
        throw Throwable("Get profile បរាជ័យ: ${e.message}")
    }
}
//    update profile
// UPDATE PROFILE
suspend fun updateProfile(user: UserData): LoginResponse {
    return try {
        ApiClient.api.updateProfile(user)
    } catch (e: Exception) {
        throw Throwable("Update profile បរាជ័យ៖ ${e.message}")
    }
}

//    upload profile
suspend fun uploadProfileImage(imageFile: File, email: String): LoginResponse {
    return try {
        val requestFile = imageFile.asRequestBody("image/*".toMediaType())
        val multipart = MultipartBody.Part.createFormData("file", imageFile.name, requestFile)
        val emailBody = email.toRequestBody("text/plain".toMediaType())

        ApiClient.api.uploadProfileImage(multipart, emailBody)

    } catch (e: Exception) {
        throw Throwable("Upload បរាជ័យ: ${e.message}")
    }
}
}

