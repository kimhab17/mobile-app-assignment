package com.example.school_system.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.compose.runtime.mutableStateOf
import com.example.school_system.data.model.User
import com.example.school_system.data.repository.UserRepository
import kotlinx.coroutines.launch

class UserViewModel : ViewModel() {

    private val repo = UserRepository()

    val users = mutableStateOf<List<User>>(emptyList())
    val message = mutableStateOf("")

    fun createUser(user: User) {
        viewModelScope.launch {
            try {
                val result = repo.createUser(user)
                message.value = "✅ User Created: ${result.name}"
            } catch (e: Exception) {
                message.value = "❌ Error: ${e.message}"
            }
        }
    }

    fun loadUsers() {
        viewModelScope.launch {
            try {
                users.value = repo.getUsers()
                message.value = "✅ Loaded ${users.value.size} users"
            } catch (e: Exception) {
                message.value = "❌ Error loading users: ${e.message}"
            }
        }
    }
}
