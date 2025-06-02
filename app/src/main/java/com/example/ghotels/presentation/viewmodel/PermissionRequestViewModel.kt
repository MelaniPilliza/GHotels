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
import com.example.ghotels.data.model.PermissionBalanceDto
import com.example.ghotels.data.utils.DateUtils
import com.example.ghotels.domain.model.PermissionRequest
import com.example.ghotels.domain.usecase.permissionrequest.GetPermissionBalancesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class PermissionRequestViewModel(
    private val addRequestUseCase: AddPermissionRequestUseCase,
    private val listAllRequestsUseCase: ListPermissionRequestUseCase,
    private val listEmployeeRequestsUseCase: ListEmployeePermissionRequestsUseCase,
    private val approveRequestUseCase: ApprovePermissionRequestUseCase,
    private val rejectRequestUseCase: RejectPermissionRequestUseCase,
    private val getPermissionBalancesUseCase: GetPermissionBalancesUseCase
) : ViewModel() {

    private val _requests = mutableStateOf<List<PermissionRequest>>(emptyList())
    val requests: State<List<PermissionRequest>> = _requests

    private val _loading = mutableStateOf(false)
    val loading: State<Boolean> = _loading

    private val _error = mutableStateOf<String?>(null)
    val error: State<String?> = _error

    private val _balances = MutableStateFlow<List<PermissionBalanceDto>>(emptyList())
    val balances: StateFlow<List<PermissionBalanceDto>> = _balances


    // DATEUTILS
    private fun convertToIsoDateTime(dateStr: String): String? {
        return DateUtils.toIsoFormat(dateStr)
    }

    // CREAR
    suspend fun createRequest(
        fromDate: String,
        toDate: String,
        comment: String,
        permissionTypeId: Long,
        employeeId: Long
    ): Boolean {
        val formattedStart = convertToIsoDateTime(fromDate)
        val formattedEnd = convertToIsoDateTime(toDate)

        if (formattedStart == null || formattedEnd == null) {
            _error.value = "Invalid date format"
            return false
        }

        val request = PermissionRequest(
            startDate = formattedStart,
            endDate = formattedEnd,
            resolutionDate = null,
            reason = comment,
            supportingDocument = null,
            status = "PENDING",
            permissionTypeId = permissionTypeId,
            employeeId = employeeId
        )
        val success = addRequestUseCase(request)
        if (success) {
            // 🔁 Cargar balances después de crear
            loadBalances(employeeId)
        }
        return success
    }


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

    fun approveRequest(id: Long, employeeId: Long) {
        viewModelScope.launch {
            val success = approveRequestUseCase(id)
            if (success) {
                _requests.value = _requests.value.map {
                    if (it.id == id) it.copy(status = "APPROVED") else it
                }
                loadBalances(employeeId)
            }
        }
    }

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

    fun loadBalances(employeeId: Long) {
        viewModelScope.launch {
            getPermissionBalancesUseCase(employeeId).onSuccess {
                _balances.value = it
            }.onFailure {
                _error.value = it.message
            }
        }
    }
}

