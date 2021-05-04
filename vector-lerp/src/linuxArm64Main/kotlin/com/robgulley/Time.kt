package com.robgulley

import kotlinx.cinterop.*
import platform.posix.*
import kotlin.time.Duration
import kotlin.time.ExperimentalTime

internal actual fun getCurrentTimeMillis(): Long = memScoped {
    val timeVal = alloc<timeval>()
    gettimeofday(timeVal.ptr, null)
    (timeVal.tv_sec * 1000) + (timeVal.tv_usec / 1000)
}

actual fun getEpochMilliForTime(day: Int, month: Int, year: Int, hour: Int, min: Int, sec: Int, tz: Int): Long {
    return memScoped {
        val timeComponents: tm = alloc()
        timeComponents.tm_mday = day
        timeComponents.tm_mon = month
        timeComponents.tm_year = year
        timeComponents.tm_hour = hour
        timeComponents.tm_min = min
        timeComponents.tm_sec = sec

        val time = mktime(timeComponents.ptr)
        time(time.toCPointer())
    }
}

internal actual fun getTimeForEpochMilli(epochMilli: Long): SimpleCalendar {
    return memScoped {
        val timeComponentsPtr = allocPointerTo<tm>()
        timeComponentsPtr.value = gmtime(epochMilli.toCPointer())
        timeComponentsPtr.pointed?.let {
            SimpleCalendar(
                day = it.tm_mday ,
                month = it.tm_mon,
                year = it.tm_year,
                hour = it.tm_hour,
                min = it.tm_min,
                sec = it.tm_sec
            )
        } ?: throw Exception("Problem with time conversion")
    }
}

actual object Sleep {
    actual fun blockFor(timeMillis: Long) {
        memScoped {
            val timespec = alloc<timespec>()
            timespec.tv_sec = timeMillis / 1000
            timespec.tv_nsec = ((timeMillis % 1000L) * 1000000L).convert()
            nanosleep(timespec.ptr, null)
        }
    }

    actual fun blockFor(millis: Long, nanos: Int) {
        val timeNanos = millis * 1000000 + nanos
        memScoped {
            val timespec = alloc<timespec>()
            timespec.tv_sec = timeNanos / 1000000000L
            timespec.tv_nsec = nanos.toLong()
            nanosleep(timespec.ptr, null)
        }
    }

    @OptIn(ExperimentalTime::class)
    actual fun blockFor(duration: Duration) {
        val (seconds, nanos) = duration.toComponents { seconds, nanoseconds -> Pair(seconds, nanoseconds) }
        memScoped {
            val timespec = alloc<timespec>()
            timespec.tv_sec = seconds
            timespec.tv_nsec = nanos.convert()
            nanosleep(timespec.ptr, null)
        }
    }
}