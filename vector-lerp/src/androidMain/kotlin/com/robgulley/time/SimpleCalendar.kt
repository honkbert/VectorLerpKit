package com.robgulley.time

import java.util.*
import java.util.Calendar.*

actual class SimpleCalendar(
    var day: Int,
    var month: Int,
    var year: Int,
    var hour: Int,
    var min: Int,
    var sec: Int,
    var tz: Int = 0, //TODO
) {

    actual companion object {
        actual fun fromEpochMilli(epochMilli: Long): SimpleCalendar {
            return getTimeForEpochMilli(epochMilli)
        }

        actual fun now(): SimpleCalendar {
            val now = Time.now().epochMilli
            return getTimeForEpochMilli(now)
        }

        private fun getTimeForEpochMilli(epochMilli: Long): SimpleCalendar {
            val calendar = getInstance()
            calendar.timeInMillis = epochMilli
            return calendar.let {
                SimpleCalendar(
                    day = it.get(DAY_OF_MONTH),
                    month = it.get(MONTH),
                    year = it.get(YEAR),
                    hour = it.get(HOUR_OF_DAY),
                    min = it.get(MINUTE),
                    sec = it.get(SECOND),
                    tz = it.get(ZONE_OFFSET),
                )
            }
        }
    }

    actual val epochTime
        get() = getEpochMilliForTime(day, month, year, hour, min, sec, tz)

    private fun getEpochMilliForTime(
        day: Int,
        month: Int,
        year: Int,
        hour: Int,
        min: Int,
        sec: Int,
        tz: Int
    ): Long {
        val calendar = getInstance()
        calendar.set(year, month, day, hour, min, sec)
        return calendar.timeInMillis
    }
}