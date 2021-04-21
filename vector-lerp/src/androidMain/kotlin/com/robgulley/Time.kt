package com.robgulley

import kotlin.time.Duration

internal actual fun getTimeNanos(): Long = System.nanoTime()
internal actual fun getCurrentTimeMillis(): Long = System.currentTimeMillis()

actual object Sleep {
    actual fun blockFor(timeMillis: Long) = Thread.sleep(timeMillis)
    actual fun blockFor(millis: Long, nanos: Int) = Thread.sleep(millis, nanos)
    actual fun blockFor(duration: Duration) {
        duration.toComponents { seconds, nanoseconds -> Thread.sleep(seconds * 1000, nanoseconds) }
    }
}