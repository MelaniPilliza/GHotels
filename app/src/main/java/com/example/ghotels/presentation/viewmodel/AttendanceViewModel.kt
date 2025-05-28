package com.example.ghotels.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ghotels.domain.model.Attendance
import com.example.ghotels.domain.usecase.attendance.GetOpenAttendanceUseCase
import com.example.ghotels.domain.usecase.attendance.ListAttendanceUseCase
import com.example.ghotels.domain.usecase.attendance.RegisterAttendanceUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.Duration
import java.time.Instant
import java.time.LocalDateTime
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit


class AttendanceViewModel(
    private val registerUseCase: RegisterAttendanceUseCase,
    private val listUseCase: ListAttendanceUseCase,
    private val getOpenAttendanceUseCase: GetOpenAttendanceUseCase
) : ViewModel() {

    private val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())

    private val _hasCheckedIn = MutableStateFlow(false)
    val hasCheckedIn: StateFlow<Boolean> = _hasCheckedIn

    private val _currentAttendance = MutableStateFlow<Attendance?>(null)
    val currentAttendance: StateFlow<Attendance?> = _currentAttendance

    private val _workedTime = MutableStateFlow("00h 00m 00s")
    val workedTime: StateFlow<String> = _workedTime

    private val _reason = MutableStateFlow("")
    val reason: StateFlow<String> = _reason

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private val _waitingForReason = MutableStateFlow(false)
    val waitingForReason: StateFlow<Boolean> = _waitingForReason

    private var counterJob: Job? = null

    /**
     * ✅ Llamar justo después del login con el ID del empleado.
     */
    fun checkOpenAttendanceOnStartup(employeeId: Long) {
        viewModelScope.launch {
            val result = getOpenAttendanceUseCase(employeeId)
            result.onSuccess { attendance ->
                _currentAttendance.value = attendance
                _hasCheckedIn.value = true
                startTimer()
            }.onFailure {
                _hasCheckedIn.value = false
            }
        }
    }

    fun registerEntry(employeeId: Long) {
        val now = formatter.format(Date())
        val attendance = Attendance(
            id = null,
            entryTime = now,
            exitTime = null,
            reasonForNonCompliance = null,
            employeeId = employeeId
        )

        viewModelScope.launch {
            val result = registerUseCase(attendance)
            result.onSuccess {
                _hasCheckedIn.value = true
                _currentAttendance.value = attendance
                _reason.value = ""
                _waitingForReason.value = false
                startTimer()
            }.onFailure {
                _error.value = it.message
            }
        }
    }

    fun registerExit(employeeId: Long, requiredHours: Int) {
        val now = formatter.format(Date())
        val attendance = _currentAttendance.value ?: return

        val duration = calculateDuration()
        val totalMinutes = duration.first * 60 + duration.second

        if (totalMinutes < requiredHours * 60 && _reason.value.isBlank()) {
            stopTimer()
            _waitingForReason.value = true
            return
        }

        val updated = attendance.copy(
            exitTime = now,
            reasonForNonCompliance = _reason.value.ifBlank { null }
        )

        viewModelScope.launch {
            val result = registerUseCase(updated)
            result.onSuccess {
                _hasCheckedIn.value = false
                _currentAttendance.value = null
                _workedTime.value = "00h 00m 00s"
                _reason.value = ""
                _waitingForReason.value = false
                stopTimer()
            }.onFailure {
                _error.value = it.message
            }
        }
    }

    fun setReason(text: String) {
        _reason.value = text
    }

    private fun startTimer() {
        stopTimer()
        counterJob = viewModelScope.launch {
            while (_hasCheckedIn.value) {
                val (hours, minutes, seconds) = calculateDuration()
                _workedTime.value = String.format(
                    Locale.getDefault(),
                    "%02dh %02dm %02ds",
                    hours,
                    minutes,
                    seconds
                )
                delay(1000)
            }
        }
    }

    private fun stopTimer() {
        counterJob?.cancel()
        counterJob = null
    }

    private fun calculateDuration(): Triple<Int, Int, Int> {
        val entry = _currentAttendance.value?.entryTime ?: return Triple(0, 0, 0)
        return try {
            val entryTime = formatter.parse(entry) ?: return Triple(0, 0, 0)
            val now = Date()
            val durationMs = now.time - entryTime.time
            val hours = TimeUnit.MILLISECONDS.toHours(durationMs).toInt()
            val minutes = TimeUnit.MILLISECONDS.toMinutes(durationMs).toInt() % 60
            val seconds = TimeUnit.MILLISECONDS.toSeconds(durationMs).toInt() % 60
            Triple(hours, minutes, seconds)
        } catch (e: Exception) {
            Triple(0, 0, 0)
        }
    }
}
