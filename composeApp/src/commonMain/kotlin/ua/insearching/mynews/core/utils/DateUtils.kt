package ua.insearching.mynews.core.utils

import androidx.compose.ui.text.intl.Locale
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.format.DateTimeComponents
import kotlinx.datetime.format.DayOfWeekNames
import kotlinx.datetime.format.FormatStringsInDatetimeFormats
import kotlinx.datetime.format.MonthNames
import kotlinx.datetime.format.byUnicodePattern
import kotlinx.datetime.format.char
import kotlinx.datetime.toInstant

object DateUtils {

    fun String.parseRFCDateTime(): LocalDateTime {
        return DateTimeComponents.Formats.RFC_1123.parse(this).toLocalDateTime()
    }

    fun String.parseISODateTime(): LocalDateTime {
        return DateTimeComponents.Formats.ISO_DATE_TIME_OFFSET.parse(this).toLocalDateTime()
    }

    fun LocalDateTime.toUTC(): String {
        return this.toInstant(TimeZone.UTC).toString()
    }

    fun LocalDateTime.formatLocalDateTime(): String {
        return this.toInstant(TimeZone.UTC).format(
            DateTimeComponents.Format {
                dayOfWeek(DayOfWeekNames.ENGLISH_FULL)
                char(',')
                char(' ')
                dayOfMonth()
                char(' ')
                monthName(MonthNames.ENGLISH_FULL)
                char(',')
                char(' ')
                year()
                char(',')
                char(' ')
                hour()
                char(':')
                minute()
            }
        )
    }
}