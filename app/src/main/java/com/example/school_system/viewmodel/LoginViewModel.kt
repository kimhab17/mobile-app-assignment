package com.example.school_system.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.school_system.data.network.ApiClient
//import com.example.school_system.model.LoginResponse
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    val message = mutableStateOf("")
    // ✅ For both student and teacher
    fun loginStudent(email: String, password: String, onSuccess: (String) -> Unit) {
        viewModelScope.launch {
            try {
                val response = ApiClient.api.loginUser(
                    mapOf("email" to email, "password" to password)
                )

                // ✅ ពិនិត្យចម្លើយ
                if (response.success) {
                    val role = response.user?.role ?: "unknown"
                    message.value = "Login success ✅ ($role)"
                    onSuccess(role)
                } else {
                    message.value = response.message
                }
            } catch (e: Exception) {
                message.value = "❌ Error: ${e.message}"
            }
        }
    }
}
