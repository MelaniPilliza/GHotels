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
    val tipoContrato: String,
    val horasLaboralesDiarias: Int,
    val rolId: Long,
    val departamentoId: Long,
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
                password= empleado.password,
                movil = empleado.movil,
                numeroSeguridadSocial = empleado.numeroSeguridadSocial,
                fechaNacimiento = empleado.fechaNacimiento,
                tipoContrato = empleado.tipoContrato,
                horasLaboralesDiarias = empleado.horasLaboralesDiarias,
                rolId = empleado.rol.id!!, // no null
                departamentoId = empleado.departamento.id!!, // no null
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