package com.robgulley

import kotlinx.cinterop.alloc
import kotlinx.cinterop.convert
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.ptr
import platform.posix.gettimeofday
import platform.posix.nanosleep
import platform.posix.timespec
import platform.posix.timeval

internal actual fun getCurrentTimeMillis(): Long = memScoped {
    val timeVal = alloc<timeval>()
    gettimeofday(timeVal.ptr, null)
    (timeVal.tv_sec * 1000) + (timeVal.tv_usec / 1000)
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
}