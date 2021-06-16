@file:Suppress("EXPERIMENTAL_IS_NOT_ENABLED")

package com.robgulley.time

import kotlin.time.Duration
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
expect class Time private constructor(markNanos: Long) {

    companion object {
        fun now(): Time
        fun Time.between(other: Time): Duration
    }

    val epochMilli: Long
    val nanoTime: Long

    fun minusMillis(millis: Long): Time
    fun plusMillis(millis: Long): Time

    fun isAfter(timeMark: Time): Boolean
    fun isBefore(timeMark: Time): Boolean
    fun elapsedThenToNow(): Duration

    operator fun minus(other: Duration): Time
    operator fun plus(other: Duration): Time
    override fun toString(): String
}