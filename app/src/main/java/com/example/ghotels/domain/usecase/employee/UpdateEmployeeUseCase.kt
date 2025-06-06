package com.example.ghotels.domain.usecase.employee

import com.example.ghotels.data.model.RegisterEmployeeDto
import com.example.ghotels.data.model.UpdateEmployeeDto
import com.example.ghotels.data.model.UserDto
import com.example.ghotels.data.repository.EmployeeRepository
import retrofit2.Response

class UpdateEmployeeUseCase(
    private val repository: EmployeeRepository
) {
    // Recibe UpdateEmployeeDto y retorna Response<UserDto>
    suspend operator fun invoke(id: Long, dto: UpdateEmployeeDto): Response<UserDto> {
        return repository.update(id, dto)
    }
}
