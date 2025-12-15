package com.example.school_system.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.school_system.data.model.UserData
import com.example.school_system.data.repository.UserRepository
import kotlinx.coroutines.launch
import java.io.File

class ProfessorViewModel : ViewModel() {

    private val repo = UserRepository()

    val profile = mutableStateOf<UserData?>(null)
    val loading = mutableStateOf(false)
    val error = mutableStateOf("")
    val message = mutableStateOf("")   // ✅ FIX: Must declare this

    // ----------------- LOAD PROFILE -----------------
    fun loadProfile(email: String) {
        viewModelScope.launch {
            loading.value = true
            try {
                val response = repo.getProfile(email)

                if (response.success) {
                    profile.value = response.user
                } else {
                    error.value = response.message
                }
            } catch (e: Exception) {
                error.value = e.message ?: "Unknown error"
            }
            loading.value = false
        }
    }

    // ----------------- UPLOAD PROFILE IMAGE -----------------
    fun uploadProfileImage(file: File, email: String) {
        viewModelScope.launch {
            loading.value = true
            try {
                val result = repo.uploadProfileImage(file, email)
                message.value = result.message    // ✅ FIXED
                loadProfile(email)                // Refresh profile
            } catch (e: Exception) {
                message.value = e.message ?: "Upload error"
            }
            loading.value = false
        }
    }
}
