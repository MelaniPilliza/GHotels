package com.example.ghotels.data.repository

import com.example.ghotels.data.model.LoginDto
import com.example.ghotels.data.model.RegistroEmpleadoDto
import com.example.ghotels.data.model.UserDto
import com.example.ghotels.data.source.remote.EmpleadoServiceClient
import com.example.ghotels.domain.model.Empleado

/**
 * Repositorio que maneja las operaciones de autenticación y registro de empleados.
 */
class EmpleadoRepository(private val empleadoServiceClient: EmpleadoServiceClient) {

    suspend fun login(loginDto: LoginDto): UserDto? {
        val response = empleadoServiceClient.login(loginDto)
        return if (response.isSuccessful) response.body() else null
    }


    suspend fun registrar(empleado: Empleado): Boolean {
        val dto = RegistroEmpleadoDto.fromEmpleado(empleado)
        return empleadoServiceClient.registrar(dto).isSuccessful
    }


    suspend fun actualizar(id: Long, empleado: Empleado): Boolean {
        val dto = RegistroEmpleadoDto.fromEmpleado(empleado)
        return empleadoServiceClient.actualizar(id, dto).isSuccessful
    }

    suspend fun obtenerEmpleados(): Result<List<UserDto>> = runCatching {
        empleadoServiceClient.obtenerEmpleados()
    }

    suspend fun actualizarPerfil(id: Long, userDto: UserDto): Result<Unit> = runCatching {
        empleadoServiceClient.actualizarPerfil(id, userDto)
    }

    suspend fun registrarEmpleado(dto: RegistroEmpleadoDto): Result<Unit> = runCatching {
        empleadoServiceClient.registrar(dto)
    }


}


