package com.example.ghotels.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ghotels.data.model.TipoPermisoDto
import com.example.ghotels.domain.usecase.TipoPermisoUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class PermissionViewModel(
    private val getTiposPermisoUseCase: TipoPermisoUseCase
) : ViewModel() {

    private val _tiposPermiso = MutableStateFlow<List<TipoPermisoDto>>(emptyList())
    val tiposPermiso: StateFlow<List<TipoPermisoDto>> = _tiposPermiso

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    init {
        cargarTiposPermiso()
    }

    private fun cargarTiposPermiso() {
        viewModelScope.launch {
            val result = getTiposPermisoUseCase()
            result.onSuccess {
                _tiposPermiso.value = it
            }.onFailure {
                _error.value = it.message
            }
        }
    }
}
