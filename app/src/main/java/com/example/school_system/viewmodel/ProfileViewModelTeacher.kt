package com.example.school_system.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.school_system.data.model.UserData
import com.example.school_system.data.repository.UserRepository
import kotlinx.coroutines.launch
import java.io.File

class ProfileViewModelTeacher : ViewModel() {

    private val repo = UserRepository()

    val profile = mutableStateOf<UserData?>(null)
    val loading = mutableStateOf(false)
    val message = mutableStateOf("")

    fun loadProfile(email: String) {
        viewModelScope.launch {
            loading.value = true
            try {
                val result = repo.getProfile(email)
                if (result.success) {
                    profile.value = result.user
                } else {
                    message.value = result.message
                }
            } catch (e: Exception) {
                message.value = e.message ?: "Unknown error"
            }
            loading.value = false
        }
    }

    fun updateProfile(user: UserData, onSuccess: () -> Unit) {
        viewModelScope.launch {
            loading.value = true
            try {
                val result = repo.updateProfile(user)
                if (result.success) {
                    message.value = "Teacher profile updated successfully!"
                    onSuccess()
                } else {
                    message.value = result.message
                }
            } catch (e: Exception) {
                message.value = e.message ?: "Unknown error"
            }
            loading.value = false
        }
    }
    fun uploadProfileImage(file: File, email: String) {
        viewModelScope.launch {
            loading.value = true
            try {
                val result = repo.uploadProfileImage(file, email)
                message.value = result.message
                loadProfile(email) // refresh UI after upload
            } catch (e: Exception) {
                message.value = e.message ?: "Upload failed"
            } finally {
                loading.value = false
            }
        }
    }
}
