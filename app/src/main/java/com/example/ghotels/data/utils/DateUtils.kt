package com.example.ghotels.data.utils

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

object DateUtils {

    private val inputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
    private val isoFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'00:00:00")

    // Convierte "28/05/2025" a "2025-05-28T00:00:00"
    fun toIsoFormat(input: String): String? {
        return try {
            val date = LocalDate.parse(input, inputFormatter)
            date.format(isoFormatter)
        } catch (e: DateTimeParseException) {
            null
        }
    }

    // Valida si la fecha tiene formato correcto "dd/MM/yyyy"
    fun isValidDate(input: String): Boolean {
        return try {
            LocalDate.parse(input, inputFormatter)
            true
        } catch (e: DateTimeParseException) {
            false
        }
    }

    // Convertir a LocalDateTime directamente
    fun toLocalDateTime(input: String): LocalDateTime? {
        return try {
            LocalDate.parse(input, inputFormatter).atStartOfDay()
        } catch (e: DateTimeParseException) {
            null
        }
    }
}