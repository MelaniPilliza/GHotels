package com.example.ghotels.data.repository

import com.example.ghotels.data.model.LoginDto
import com.example.ghotels.data.model.LoginRespuestaDto
import com.example.ghotels.data.model.RegistroEmpleadoDto
import com.example.ghotels.data.source.remote.EmpleadoServiceClient
import com.example.ghotels.domain.model.Empleado

/**
 * Repositorio que maneja las operaciones de autenticación y registro de empleados.
 */
class EmpleadoRepository(private val service: EmpleadoServiceClient) {

    suspend fun login(loginDto: LoginDto): LoginRespuestaDto? {
        val response = service.login(loginDto)
        return if (response.isSuccessful) response.body() else null
    }


    suspend fun registrar(empleado: Empleado): Boolean {
        val dto = RegistroEmpleadoDto.fromEmpleado(empleado)
        return service.registrar(dto).isSuccessful
    }


    suspend fun actualizar(id: Long, empleado: Empleado): Boolean {
        val dto = RegistroEmpleadoDto.fromEmpleado(empleado)
        return service.actualizar(id, dto).isSuccessful
    }
}


