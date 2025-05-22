package com.example.ghotels.data.model

data class LoginRespuestaDto(
    val id: Long,
    val nombre: String,
    val apellidos: String,
    val mail: String,
    val movil: String,
    val dni: String,
    val numeroSeguridadSocial: String,
    val fechaNacimiento: String,

    val nacionalidad: String? = null,
    val genero: String? = null,
    val estadoCivil: String? = null,
    val numeroHijos: Int? = null,
    val discapacidad: Boolean? = null,

    val tipoContrato: String,
    val horasLaboralesDiarias: Int,
    val fechaIngreso: String,

    val rol: String,
    val departamento: String,
    val nombreSupervisor: String? = null,
    val rolSupervisor: String? = null,

    val contactoEmergencia: ContactoEmergenciaDto? = null,
    val asistencias: List<AsistenciaDto> = emptyList(),
    val permisos: List<SolicitudPermisoDto> = emptyList()
)
