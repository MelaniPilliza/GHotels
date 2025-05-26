package com.example.ghotels.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.ghotels.domain.usecase.SaveUserUseCase
import com.example.ghotels.domain.usecase.UpdateProfileUseCase
import androidx.lifecycle.viewModelScope
import com.example.ghotels.data.model.ContactoEmergenciaDto
import com.example.ghotels.data.model.DireccionDto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val updateProfileUseCase: UpdateProfileUseCase,
    private val saveUserUseCase: SaveUserUseCase
) : ViewModel() {

    val usuario = saveUserUseCase.usuario

    private val _estadoGuardado = MutableStateFlow<Boolean?>(null)
    val estadoGuardado: StateFlow<Boolean?> = _estadoGuardado

    fun setCampo(campo: String, valor: String) {
        val actual = usuario.value ?: return
        val direccionActual = actual.direccion ?: DireccionDto("", "", "", "", "")
        val direccionActualizada = when (campo) {
            "direccionCalle" -> direccionActual.copy(calle = valor)
            "direccionCiudad" -> direccionActual.copy(ciudad = valor)
            "direccionProvincia" -> direccionActual.copy(provincia = valor)
            "direccionCodigoPostal" -> direccionActual.copy(codigoPostal = valor)
            "direccionPais" -> direccionActual.copy(pais = valor)
            else -> direccionActual
        }

        val actualizado = when (campo) {
            "mail" -> actual.copy(mail = valor)
            "movil" -> actual.copy(movil = valor)
            "estadoCivil" -> actual.copy(estadoCivil = valor)
            "nacionalidad" -> actual.copy(nacionalidad = valor)
            "genero" -> actual.copy(genero = valor)
            "direccionCalle", "direccionCiudad", "direccionProvincia", "direccionCodigoPostal", "direccionPais" -> actual.copy(direccion = direccionActualizada)
            else -> actual
        }

        saveUserUseCase.setUsuario(actualizado)
    }

    /**
     * Modifica el contacto de emergencia (lo crea si no existe)
     */
    fun setContactoEmergencia(nombre: String, telefono: String, relacion: String) {
        val actual = usuario.value ?: return
        val contacto = actual.contactoEmergencia ?: ContactoEmergenciaDto(
            id = null,
            nombre = "",
            telefono = "",
            relacion = "",
            empleadoId = actual.id
        )

        val contactoActualizado = contacto.copy(
            nombre = nombre,
            telefono = telefono,
            relacion = relacion
        )

        val actualizado = actual.copy(contactoEmergencia = contactoActualizado)
        saveUserUseCase.setUsuario(actualizado)
    }

    fun guardarCambios() {
        val actual = usuario.value ?: return
        viewModelScope.launch {
            val result = updateProfileUseCase(actual.id, actual)
            _estadoGuardado.value = result.isSuccess
        }
    }
}