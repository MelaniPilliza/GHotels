package com.example.ghotels.data.model

import com.example.ghotels.domain.model.OfficialHoliday

data class OfficialHolidayDto(
    val id: Long? = null,
    val date: String,
    val name: String
) {
    fun toHoliday(): OfficialHoliday = OfficialHoliday(
        id = id,
        date = date,
        name = name
    )

    companion object {
        fun from(holiday: OfficialHoliday) = OfficialHolidayDto(
            id = holiday.id,
            date = holiday.date,
            name = holiday.name
        )
    }
}

