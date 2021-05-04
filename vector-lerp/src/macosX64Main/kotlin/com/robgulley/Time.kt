package com.robgulley

import kotlinx.cinterop.*
import platform.posix.gettimeofday
import platform.posix.nanosleep
import platform.posix.timespec
import platform.posix.timeval
import kotlin.time.Duration
import kotlin.time.ExperimentalTime

internal actual fun getCurrentTimeMillis(): Long = memScoped {
    val timeVal = alloc<timeval>()
    gettimeofday(timeVal.ptr, null)
    (timeVal.tv_sec * 1000) + (timeVal.tv_usec / 1000)
}

actual fun getEpochMilliForTime(day: Int, month: Int, year: Int, hour: Int, min: Int, sec: Int, tz: Int): Long {
    return 0 //TODO
}

internal actual fun getTimeForEpochMilli(epochMilli: Long): SimpleCalendar {
    TODO("not implemented")
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