package com.example.ghotels.data.repository

import com.example.ghotels.data.model.OfficialHolidayDto
import com.example.ghotels.data.source.remote.OfficialHolidayServiceClient

class OfficialHolidayRepository(
    private val holidayServiceClient: OfficialHolidayServiceClient
) {
    suspend fun getAll(): List<OfficialHolidayDto> {
        return holidayServiceClient.getAll()
    }

    suspend fun add(dto: OfficialHolidayDto): Boolean {
        return holidayServiceClient.add(dto).isSuccessful
    }

    suspend fun update(id: Long, dto: OfficialHolidayDto): Boolean {
        return holidayServiceClient.update(id, dto).isSuccessful
    }

    suspend fun delete(id: Long): Boolean {
        return holidayServiceClient.delete(id).isSuccessful
    }
}