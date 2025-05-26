package com.example.ghotels.data.model

import com.example.ghotels.domain.model.Departamento
import com.example.ghotels.domain.model.Empleado
import com.example.ghotels.domain.model.Rol

data class RegistroEmpleadoDto(
    val id: Long? = null,
    val dni: String,
    val nombre: String,
    val apellidos: String,
    val mail: String,
    val password: String,
    val movil: String,
    val numeroSeguridadSocial: String,
    val fechaNacimiento: String,
    val fechaIngreso: String?,
    val tipoContrato: String,
    val horasLaboralesDiarias: Int,
    val rolId: Long,
    val departamentoId: Long,
    val supervisorId: Long? = null, // ✅ Añadido
    val direccion: DireccionDto? = null,
    val nacionalidad: String? = null,
    val genero: String? = null,
    val estadoCivil: String? = null,
    val numeroHijos: Int? = null,
    val discapacidad: Boolean? = null
) {
    companion object {
        fun fromEmpleado(empleado: Empleado): RegistroEmpleadoDto {
            return RegistroEmpleadoDto(
                id = empleado.id,
                dni = empleado.dni,
                nombre = empleado.nombre,
                apellidos = empleado.apellidos,
                mail = empleado.mail,
                password = empleado.password, // Asegúrate de NO enviar la password real en producción
                movil = empleado.movil,
                numeroSeguridadSocial = empleado.numeroSeguridadSocial,
                fechaNacimiento = empleado.fechaNacimiento,
                fechaIngreso = empleado.fechaIngreso,
                tipoContrato = empleado.tipoContrato,
                horasLaboralesDiarias = empleado.horasLaboralesDiarias,
                rolId = empleado.rol.id!!,
                departamentoId = empleado.departamento.id!!,
                supervisorId = empleado.supervisorId , // ✅ Añadido
                direccion = empleado.direccion?.let {
                    DireccionDto(
                        calle = it.calle,
                        codigoPostal = it.codigoPostal,
                        ciudad = it.ciudad,
                        provincia = it.provincia,
                        pais = it.pais
                    )
                },
                nacionalidad = empleado.nacionalidad,
                genero = empleado.genero,
                estadoCivil = empleado.estadoCivil,
                numeroHijos = empleado.numeroHijos,
                discapacidad = empleado.discapacidad
            )
        }
    }
}
