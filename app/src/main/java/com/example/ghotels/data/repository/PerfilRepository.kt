package com.example.ghotels.data.repository

import com.example.ghotels.data.model.ContactoEmergenciaDto
import com.example.ghotels.data.model.PerfilDto
import com.example.ghotels.data.source.remote.PerfilServiceClient

/**
 * Repositorio que permite actualizar los datos personales y de contacto del empleado.
 */
class PerfilRepository(val service: PerfilServiceClient) {


    suspend fun actualizarPerfil(empleadoId: Long, dto: PerfilDto): Boolean {
        return try {
            service.actualizarPerfil(empleadoId, dto).isSuccessful
        } catch (e: Exception) {
            false
        }
    }

    suspend fun actualizarContactoEmergencia(empleadoId: Long, dto: ContactoEmergenciaDto): Boolean {
        return try {
            service.actualizarContacto(empleadoId, dto).isSuccessful
        } catch (e: Exception) {
            false
        }
    }
}
