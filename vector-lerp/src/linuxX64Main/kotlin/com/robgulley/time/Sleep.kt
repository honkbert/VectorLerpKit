@file:Suppress("EXPERIMENTAL_IS_NOT_ENABLED")

package com.robgulley.time

import kotlinx.cinterop.alloc
import kotlinx.cinterop.convert
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.ptr
import platform.posix.nanosleep
import platform.posix.timespec
import kotlin.time.Duration

actual object Sleep {
    actual fun blockFor(timeMillis: Long) {
        memScoped {
            val timeSpec = alloc<timespec>()
            timeSpec.tv_sec = timeMillis / 1_000
            timeSpec.tv_nsec = ((timeMillis % 1_000L) * 1_000_000L).convert()
            nanosleep(timeSpec.ptr, null)
        }
    }

    actual fun blockFor(millis: Long, nanos: Long) {
        val timeNanos = millis * 1_000_000 + nanos
        memScoped {
            val timespec = alloc<timespec>()
            timespec.tv_sec = timeNanos / 1_000_000_000L
            timespec.tv_nsec = nanos
            nanosleep(timespec.ptr, null)
        }
    }

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