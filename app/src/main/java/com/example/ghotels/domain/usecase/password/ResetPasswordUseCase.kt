package com.example.ghotels.domain.usecase.password

import com.example.ghotels.data.repository.PasswordResetTokenRepository

class ResetPasswordUseCase(
    private val repository: PasswordResetTokenRepository
) {
    suspend operator fun invoke(token: String, newPassword: String): Boolean {
        val response = repository.resetPassword(token, newPassword)
        return response.isSuccessful
    }
}