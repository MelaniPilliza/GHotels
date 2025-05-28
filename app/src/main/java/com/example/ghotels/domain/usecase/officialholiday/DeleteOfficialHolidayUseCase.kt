package com.example.ghotels.domain.usecase.officialholiday

import com.example.ghotels.data.repository.OfficialHolidayRepository

class DeleteOfficialHolidayUseCase(
    private val repository: OfficialHolidayRepository
) {
    suspend operator fun invoke(id: Long?): Boolean {
        if (id == null) return false // No se puede eliminar sin ID
        return repository.delete(id)
    }
}
