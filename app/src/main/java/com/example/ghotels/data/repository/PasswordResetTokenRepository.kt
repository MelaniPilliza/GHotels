package com.example.ghotels.data.repository

import com.example.ghotels.data.model.RecoverRequestDto
import com.example.ghotels.data.model.ResetPasswordDto
import com.example.ghotels.data.source.remote.PasswordResetTokenServiceClient
import retrofit2.Response

class PasswordResetTokenRepository(
    private val serviceClient: PasswordResetTokenServiceClient
) {
    suspend fun requestRecovery(email: String): Response<Void> {
        val dto = RecoverRequestDto(email = email)
        return serviceClient.recover(dto)
    }

    suspend fun resetPassword(token: String, newPassword: String): Response<Void> {
        val dto = ResetPasswordDto(token = token, newPassword = newPassword)
        return serviceClient.resetPassword(dto)
    }
}