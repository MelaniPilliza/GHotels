package com.example.ghotels.domain.usecase.officialholiday

import com.example.ghotels.data.model.OfficialHolidayDto
import com.example.ghotels.data.repository.OfficialHolidayRepository
import com.example.ghotels.domain.model.OfficialHoliday

class UpdateOfficialHolidayUseCase(
    private val repository: OfficialHolidayRepository
) {
    suspend operator fun invoke(holiday: OfficialHoliday): Boolean {
        val id = holiday.id ?: return false
        val dto = OfficialHolidayDto.from(holiday)
        return repository.update(id, dto)
    }
}
