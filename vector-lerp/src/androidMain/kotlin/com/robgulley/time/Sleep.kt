package com.robgulley.time

import kotlin.time.Duration
import kotlin.time.ExperimentalTime

actual object Sleep {
    actual fun blockFor(timeMillis: Long) = Thread.sleep(timeMillis)
    actual fun blockFor(millis: Long, nanos: Long) = Thread.sleep(millis, nanos.toInt())

    @OptIn(ExperimentalTime::class)
    actual fun blockFor(duration: Duration) {
        duration.toComponents { seconds, nanoseconds -> Thread.sleep(seconds * 1000, nanoseconds) }
    }
}