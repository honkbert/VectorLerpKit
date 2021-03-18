package com.robgulley

import kotlin.time.DurationUnit
import kotlin.time.ExperimentalTime
import kotlin.time.toDuration

@OptIn(ExperimentalTime::class)
class Time(private val markNanos: Long) {
    private val markMillis = markNanos / 1000000

    companion object {
        fun now() = Time(getTimeNanos())
    }

    fun minusMillis(millis: Long) = Time(markMillis - millis)
    fun toSystemMilli() = markMillis
    fun isAfter(timeMark: Time): Boolean = this.markNanos > timeMark.markNanos
    fun isBefore(timeMark: Time): Boolean = this.markNanos < timeMark.markNanos

    operator fun minus(other: Time) = (this.markNanos - other.markNanos).toDuration(DurationUnit.NANOSECONDS)
}

expect fun getTimeNanos(): Long