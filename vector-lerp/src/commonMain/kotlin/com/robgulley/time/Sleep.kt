package com.robgulley.time

import kotlin.time.Duration

expect object Sleep {
    fun blockFor(timeMillis: Long)
    fun blockFor(millis: Long, nanos: Long)
    fun blockFor(duration: Duration)
}