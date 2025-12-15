package com.example.school_system.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.compose.runtime.mutableStateOf
import com.example.school_system.data.model.UserData
import com.example.school_system.data.repository.UserRepository
import kotlinx.coroutines.launch

class UserViewModel : ViewModel() {

    private val repo = UserRepository()

    val users = mutableStateOf<List<UserData>>(emptyList())
    val message = mutableStateOf("")

    // ✅ Register new user
    fun createUser(user: UserData) {
        viewModelScope.launch {
            try {
                val result = repo.registerUser(user)
                message.value = "✅ Register success: ${result.name}"
            } catch (e: Exception) {
                message.value = "❌ Error: ${e.message}"
            }
        }
    }
}
