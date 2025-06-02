package com.example.ghotels.data.source.remote

import com.example.ghotels.data.model.RecoverRequestDto
import com.example.ghotels.data.model.ResetPasswordDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface PasswordResetTokenServiceClient {
    @POST("/api/usuario/recover")
    suspend fun recover(@Body dto: RecoverRequestDto): Response<Void>

    @POST("/api/usuario/reset-password")
    suspend fun resetPassword(@Body dto: ResetPasswordDto): Response<Void>
}