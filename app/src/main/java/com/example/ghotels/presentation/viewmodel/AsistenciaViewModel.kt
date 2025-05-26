package com.example.ghotels.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ghotels.data.model.AsistenciaDto
import com.example.ghotels.domain.usecase.AsistenciaUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.Duration
import java.time.LocalDateTime
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit


class AsistenciaViewModel(
    private val useCase: AsistenciaUseCase
) : ViewModel() {

    // Guarda el objeto con fecha de entrada y salida.
    private val _asistenciaActual = MutableStateFlow<AsistenciaDto?>(null)
    val asistenciaActual: StateFlow<AsistenciaDto?> = _asistenciaActual

    // Calcula el tiempo transcurrido y lo actualiza cada segundo.
    private val _tiempoActual = MutableStateFlow(Triple(0, 0, 0))
    val tiempoActual: StateFlow<Triple<Int, Int, Int>> = _tiempoActual

    // Indica si el empleado ha pulsado "Entrar".
    private val _haEntrado = MutableStateFlow(false)
    val haEntrado: StateFlow<Boolean> = _haEntrado

    private val _motivo = MutableStateFlow("")
    val motivo: StateFlow<String> = _motivo

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private val _mostrarAdvertencia = MutableStateFlow(false)
    val mostrarAdvertencia: StateFlow<Boolean> = _mostrarAdvertencia

    //	Controla si se debe mostrar el campo de texto para explicar por qué no cumplió.
    private val _esperandoMotivo = MutableStateFlow(false)
    val esperandoMotivo: StateFlow<Boolean> = _esperandoMotivo

    //	Un Job que corre en segundo plano actualizando cada segundo el contador.
    private val formato = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
    private var contadorJob: Job? = null


    fun registrarEntrada(idEmpleado: Long) {
        val ahora = formato.format(Date())
        val dto = AsistenciaDto(
            entrada = ahora,
            salida = null,
            motivoNoCumplimiento = null,
            empleadoId = idEmpleado
        )

        viewModelScope.launch {
            val result = useCase.registrarAsistencia(dto)
            result.onSuccess {
                _haEntrado.value = true
                _asistenciaActual.value = dto
                _motivo.value = ""
                _mostrarAdvertencia.value = false
                _esperandoMotivo.value = false
                iniciarContador()
            }.onFailure {
                _error.value = it.message
            }
        }
    }

    fun registrarSalida(idEmpleado: Long, horasRequeridas: Int) {
        val asistencia = _asistenciaActual.value ?: return
        val salida = formato.format(Date())

        val duracion = calcularDuracion()
        val totalMinutos = duracion.first * 60 + duracion.second

        if (totalMinutos < horasRequeridas * 60 && _motivo.value.isBlank()) {
            detenerContador()
            _mostrarAdvertencia.value = true
            _esperandoMotivo.value = true
            return
        }

        val dto = asistencia.copy(
            salida = salida,
            motivoNoCumplimiento = _motivo.value.ifBlank { null },
            empleadoId = idEmpleado
        )

        viewModelScope.launch {
            val result = useCase.registrarAsistencia(dto)
            result.onSuccess {
                detenerContador()
                _haEntrado.value = false
                _asistenciaActual.value = null
                _tiempoActual.value = Triple(0, 0, 0)
                _mostrarAdvertencia.value = false
                _esperandoMotivo.value = false
                _motivo.value = ""
            }.onFailure {
                _error.value = it.message
            }
        }
    }

    fun setMotivo(texto: String) {
        _motivo.value = texto
    }

    fun calcularDuracion(): Triple<Int, Int, Int> {
        val entrada = asistenciaActual.value?.entrada ?: return Triple(0, 0, 0)
        return try {
            val entradaHora = formato.parse(entrada) ?: return Triple(0, 0, 0)
            val ahora = Date()
            val duracionMs = ahora.time - entradaHora.time
            val horas = TimeUnit.MILLISECONDS.toHours(duracionMs).toInt()
            val minutos = TimeUnit.MILLISECONDS.toMinutes(duracionMs).toInt() % 60
            val segundos = TimeUnit.MILLISECONDS.toSeconds(duracionMs).toInt() % 60
            Triple(horas, minutos, segundos)
        } catch (e: Exception) {
            Triple(0, 0, 0)
        }
    }

    private fun iniciarContador() {
        detenerContador()
        contadorJob = viewModelScope.launch {
            while (_haEntrado.value) {
                _tiempoActual.value = calcularDuracion()
                delay(1000)
            }
        }
    }

    private fun detenerContador() {
        contadorJob?.cancel()
        contadorJob = null
    }
}
