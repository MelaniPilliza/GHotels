package com.example.ghotels.domain.usecase.password

import com.example.ghotels.data.repository.PasswordResetTokenRepository

class RecoverPasswordUseCase(
    private val repository: PasswordResetTokenRepository
) {
    suspend operator fun invoke(email: String): Boolean {
        val response = repository.requestRecovery(email)
        return response.isSuccessful
    }
}