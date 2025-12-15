package com.example.school_system.data.model

data class UserData(
    val name: String?,
    val email: String?,
    val password: String?, // ✅ បន្ថែមប៉ុណ្ណេះ
    val role: String?,
    val age: Int?
)