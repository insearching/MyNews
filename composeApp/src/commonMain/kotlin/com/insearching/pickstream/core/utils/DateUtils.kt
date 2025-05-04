package com.insearching.pickstream.core.utils

import com.diamondedge.logging.logging
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.format.DateTimeComponents
import kotlinx.datetime.format.MonthNames
import kotlinx.datetime.format.char
import kotlinx.datetime.toInstant

object DateUtils {

    fun String.parseRFCDateTime(): LocalDateTime? {
        return try {
            DateTimeComponents.Formats.RFC_1123.parse(this).toLocalDateTime()
        } catch (ex: Exception) {
            DateTimeComponents.Formats.ISO_DATE_TIME_OFFSET.parse(this).toLocalDateTime()
        } catch (ex: Exception) {
            log.i { ex.message }
            null
        }
    }

    fun String.parseISODateTime(): LocalDateTime? {
        return try {
            DateTimeComponents.Formats.ISO_DATE_TIME_OFFSET.parse(this).toLocalDateTime()
        } catch (ex: Exception) {
            log.i { ex.message }
            null
        }
    }

    fun LocalDateTime.toUTC(): String {
        return this.toInstant(TimeZone.UTC).toString()
    }

    fun LocalDateTime.formatLocalDateTime(): String {
        return this.toInstant(TimeZone.UTC).format(
            DateTimeComponents.Format {
                monthName(MonthNames.ENGLISH_ABBREVIATED)
                char(' ')
                dayOfMonth()
                char(',')
                char(' ')
                year()
            }
        )
    }


    val log = logging()
}