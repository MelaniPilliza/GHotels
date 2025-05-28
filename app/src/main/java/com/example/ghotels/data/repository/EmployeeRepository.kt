package com.example.ghotels.data.repository

import com.example.ghotels.data.model.LoginDto
import com.example.ghotels.data.model.RegisterEmployeeDto
import com.example.ghotels.data.model.UserDto
import com.example.ghotels.data.source.remote.EmployeeServiceClient
import com.example.ghotels.domain.model.Employee
import retrofit2.Response

class EmployeeRepository(private val service: EmployeeServiceClient) {

    suspend fun login(loginDto: LoginDto): UserDto? {
        val response = service.login(loginDto)
        return if (response.isSuccessful) response.body() else null
    }

    suspend fun register(dto: RegisterEmployeeDto): Response<Unit> = service.register(dto)

    suspend fun update(id: Long, dto: RegisterEmployeeDto): Response<Unit> = service.update(id, dto)

    suspend fun getAll(): List<UserDto> = service.getEmployees()

    suspend fun updateProfile(id: Long, dto: UserDto): Result<Unit> = runCatching {
        service.updateProfile(id, dto)
        Unit
    }
}

