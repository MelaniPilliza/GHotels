package com.example.ghotels.data.model

data class PerfilDto(
    val correo: String,
    val movil: String,
    val direccion: DireccionDto? = null,
    val nacionalidad: String? = null,
    val genero: String? = null,
    val estadoCivil: String? = null,
    val numeroHijos: Int? = null,
    val discapacidad: Boolean? = null
)
