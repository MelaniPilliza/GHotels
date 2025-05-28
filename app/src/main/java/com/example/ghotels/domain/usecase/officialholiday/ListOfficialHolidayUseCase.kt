package com.example.ghotels.domain.usecase.officialholiday

import com.example.ghotels.data.repository.OfficialHolidayRepository
import com.example.ghotels.domain.model.OfficialHoliday

class ListOfficialHolidayUseCase(
    private val repository: OfficialHolidayRepository
) {
    suspend operator fun invoke(): List<OfficialHoliday> {
        return repository.getAll().map { it.toHoliday() }
    }
}