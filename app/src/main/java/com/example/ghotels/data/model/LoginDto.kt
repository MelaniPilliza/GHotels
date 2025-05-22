package com.example.ghotels.data.model

import com.example.ghotels.domain.model.Departamento
import com.example.ghotels.domain.model.Empleado
import com.example.ghotels.domain.model.Rol

data class LoginDto(
    val mail: String,
    val password: String
)


