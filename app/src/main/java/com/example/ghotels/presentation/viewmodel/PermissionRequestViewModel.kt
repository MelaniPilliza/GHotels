package com.example.ghotels.presentation.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.example.ghotels.domain.usecase.permissionrequest.AddPermissionRequestUseCase
import com.example.ghotels.domain.usecase.permissionrequest.ApprovePermissionRequestUseCase
import com.example.ghotels.domain.usecase.permissionrequest.ListEmployeePermissionRequestsUseCase
import com.example.ghotels.domain.usecase.permissionrequest.ListPermissionRequestUseCase
import com.example.ghotels.domain.usecase.permissionrequest.RejectPermissionRequestUseCase
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ghotels.domain.model.PermissionRequest
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class PermissionRequestViewModel(
    private val addRequestUseCase: AddPermissionRequestUseCase,
    private val listAllRequestsUseCase: ListPermissionRequestUseCase,
    private val listEmployeeRequestsUseCase: ListEmployeePermissionRequestsUseCase,
    private val approveRequestUseCase: ApprovePermissionRequestUseCase,
    private val rejectRequestUseCase: RejectPermissionRequestUseCase
) : ViewModel() {

    private val _requests = mutableStateOf<List<PermissionRequest>>(emptyList())
    val requests: State<List<PermissionRequest>> = _requests

    private val _loading = mutableStateOf(false)
    val loading: State<Boolean> = _loading

    private val _error = mutableStateOf<String?>(null)
    val error: State<String?> = _error

    private val formatterInput = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    private val formatterOutput = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())

    private fun convertToIsoDateTime(dateStr: String): String {
        return try {
            val date = formatterInput.parse(dateStr)
            formatterOutput.format(date ?: Date())
        } catch (e: Exception) {
            ""
        }
    }

    // Para crear una solicitud
    suspend fun createRequest(
        fromDate: String,
        toDate: String,
        comment: String,
        permissionTypeId: Long,
        employeeId: Long
    ): Boolean {
        val formattedStart = convertToIsoDateTime(fromDate)
        val formattedEnd = convertToIsoDateTime(toDate)

        val request = PermissionRequest(
            startDate = formattedStart,
            endDate = formattedEnd,
            resolutionDate = null,
            reason = comment,
            permissionTypeId = permissionTypeId,
            employeeId = employeeId
        )
        return addRequestUseCase(request)
    }

    // Para empleados: listar sus propias solicitudes
    fun loadRequestsByEmployee(employeeId: Long) {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null
            listEmployeeRequestsUseCase(employeeId).onSuccess {
                _requests.value = it
            }.onFailure {
                _error.value = it.message
            }
            _loading.value = false
        }
    }

    // Para admins: listar todas las solicitudes
    fun loadAllRequests() {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null
            listAllRequestsUseCase().onSuccess {
                _requests.value = it
            }.onFailure {
                _error.value = it.message
            }
            _loading.value = false
        }
    }

    // Aprobar solicitud
    fun approveRequest(id: Long) {
        viewModelScope.launch {
            val success = approveRequestUseCase(id)
            if (success) {
                _requests.value = _requests.value.map {
                    if (it.id == id) it.copy(status = "APPROVED") else it
                }
            }
        }
    }

    // Rechazar solicitud
    fun rejectRequest(id: Long) {
        viewModelScope.launch {
            val success = rejectRequestUseCase(id)
            if (success) {
                _requests.value = _requests.value.map {
                    if (it.id == id) it.copy(status = "REJECTED") else it
                }
            }
        }
    }
}

