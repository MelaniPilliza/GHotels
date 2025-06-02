package com.example.ghotels.data.model

data class ResetPasswordDto(
    val token: String,
    val newPassword: String
)