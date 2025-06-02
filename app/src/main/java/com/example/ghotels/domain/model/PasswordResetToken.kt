package com.example.ghotels.domain.model

data class PasswordResetToken(
    val id: Long? = null,
    val token: String,
    val expiresAt: String,
    val employeeId: Long
)