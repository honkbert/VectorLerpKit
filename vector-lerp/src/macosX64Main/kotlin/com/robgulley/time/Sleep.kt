package com.robgulley.time

import kotlinx.cinterop.alloc
import kotlinx.cinterop.convert
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.ptr
import platform.posix.nanosleep
import platform.posix.timespec
import kotlin.time.Duration
import kotlin.time.ExperimentalTime

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