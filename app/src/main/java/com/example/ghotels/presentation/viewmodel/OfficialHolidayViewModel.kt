package com.example.ghotels.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ghotels.data.model.OfficialHolidayDto
import com.example.ghotels.domain.model.OfficialHoliday
import com.example.ghotels.domain.usecase.officialholiday.AddOfficialHolidayUseCase
import com.example.ghotels.domain.usecase.officialholiday.DeleteOfficialHolidayUseCase
import com.example.ghotels.domain.usecase.officialholiday.ListOfficialHolidayUseCase
import com.example.ghotels.domain.usecase.officialholiday.UpdateOfficialHolidayUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class OfficialHolidayViewModel(
    private val listUseCase: ListOfficialHolidayUseCase,
    private val addUseCase: AddOfficialHolidayUseCase,
    private val updateUseCase: UpdateOfficialHolidayUseCase,
    private val deleteUseCase: DeleteOfficialHolidayUseCase
) : ViewModel() {

    private val _holidays = MutableStateFlow<List<OfficialHoliday>>(emptyList())
    val holidays: StateFlow<List<OfficialHoliday>> = _holidays

    private val _loading = MutableStateFlow(true)
    val loading: StateFlow<Boolean> = _loading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    init {
        loadHolidays()
    }

    fun loadHolidays() {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null

            try {
                _holidays.value = listUseCase()
            } catch (e: Exception) {
                _error.value = "Error al cargar festivos"
            } finally {
                _loading.value = false
            }
        }
    }

    fun addHoliday(name: String, date: String) {
        viewModelScope.launch {
            try {
                val nuevoFestivo = OfficialHoliday(name = name, date = date)
                val success = addUseCase(nuevoFestivo)
                if (success) {
                    loadHolidays()
                } else {
                    _error.value = "Error al crear el festivo"
                }
            } catch (e: Exception) {
                _error.value = "Error inesperado al añadir festivo"
            }
        }
    }

    fun updateHoliday(id: Long?, name: String, date: String) {
        viewModelScope.launch {
            try {
                val actualizado = OfficialHoliday(id = id, name = name, date = date)
                val success = updateUseCase(actualizado)
                if (success) {
                    loadHolidays()
                } else {
                    _error.value = "Error al actualizar el festivo"
                }
            } catch (e: Exception) {
                _error.value = "Error inesperado al actualizar festivo"
            }
        }
    }

    fun deleteHoliday(id: Long?) {
        viewModelScope.launch {
            try {
                val success = deleteUseCase(id)
                if (success) {
                    loadHolidays()
                } else {
                    _error.value = "Error al eliminar el festivo"
                }
            } catch (e: Exception) {
                _error.value = "Error inesperado al eliminar festivo"
            }
        }
    }
}
