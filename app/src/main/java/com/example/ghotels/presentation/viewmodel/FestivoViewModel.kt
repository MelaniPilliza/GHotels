package com.example.ghotels.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ghotels.data.model.FestivoDto
import com.example.ghotels.domain.usecase.FestivoUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FestivoViewModel(
    private val festivoUseCase: FestivoUseCase
) : ViewModel() {

    private val _festivos = MutableStateFlow<List<FestivoDto>>(emptyList())
    val festivos: StateFlow<List<FestivoDto>> = _festivos

    private val _cargando = MutableStateFlow(true)
    val cargando: StateFlow<Boolean> = _cargando

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    init {
        cargarFestivos()
    }

    /**
     * Carga los festivos desde el backend usando el use case.
     * Actualiza el estado observable con la lista recibida o muestra error si falla.
     */
    fun cargarFestivos() {
        viewModelScope.launch {
            _cargando.value = true
            _error.value = null

            festivoUseCase().onSuccess { lista ->
                _festivos.value = lista
            }.onFailure {
                _error.value = "❌ Error al cargar festivos"
                _festivos.value = emptyList()
            }

            _cargando.value = false
        }
    }
}
