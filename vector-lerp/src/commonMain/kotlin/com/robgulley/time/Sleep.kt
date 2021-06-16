@file:Suppress("EXPERIMENTAL_IS_NOT_ENABLED")

package com.robgulley.time

import kotlin.time.Duration
import kotlin.time.ExperimentalTime

expect object Sleep {
    fun blockFor(timeMillis: Long)
    fun blockFor(millis: Long, nanos: Long)

    @OptIn(ExperimentalTime::class)
    fun blockFor(duration: Duration)
}