package com.example.ghotels.domain.model

data class Empleado(
    val id: Long? = null,
    val dni: String,
    val nombre: String,
    val apellidos: String,
    val mail: String,
    val password: String,
    val movil: String,
    val numeroSeguridadSocial: String,
    val fechaNacimiento: String,
    val direccion: Direccion? = null,
    val nacionalidad: String? = null,
    val genero: String? = null,
    val estadoCivil: String? = null,
    val numeroHijos: Int = 0,
    val discapacidad: Boolean = false,
    val tipoContrato: String,
    val horasLaboralesDiarias: Int,
    val fechaIngreso: String? = null,
    val supervisorId: Long? = null,
    val rol: Rol,
    val departamento: Departamento,
    val contactoEmergencia: ContactoEmergencia? = null
)


