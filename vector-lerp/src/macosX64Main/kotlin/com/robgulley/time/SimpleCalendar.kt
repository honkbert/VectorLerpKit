package com.robgulley.time

import kotlinx.cinterop.*
import platform.posix.*

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
            return memScoped {
                val timeComponentsPtr = allocPointerTo<tm>()
                val timestamp: time_tVar = alloc()
                timestamp.value = epochMilli
                timeComponentsPtr.value = gmtime(timestamp.ptr)
                timeComponentsPtr.pointed?.let {
                    SimpleCalendar(
                        day = it.tm_mday,
                        month = it.tm_mon,
                        year = it.tm_year,
                        hour = it.tm_hour,
                        min = it.tm_min,
                        sec = it.tm_sec
                    )
                } ?: throw Exception("Problem with time conversion")
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
        return memScoped {
            val timeComponents: tm = alloc()
            timeComponents.tm_mday = day
            timeComponents.tm_mon = month
            timeComponents.tm_year = year
            timeComponents.tm_hour = hour
            timeComponents.tm_min = min
            timeComponents.tm_sec = sec
            //timeComponents.tm_zone = 0

            val timeTvar: time_tVar = alloc()
            timeTvar.value = mktime(timeComponents.ptr)
            time(timeTvar.ptr)
        }
    }
}