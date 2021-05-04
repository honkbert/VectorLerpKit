package com.robgulley

import kotlin.time.Duration
import kotlin.time.DurationUnit
import kotlin.time.ExperimentalTime
import kotlin.time.toDuration

@OptIn(ExperimentalTime::class)
class Time(private val markNanos: Long) {
    private val markMillis =
        Duration.convert(markNanos.toDouble(), DurationUnit.NANOSECONDS, DurationUnit.MILLISECONDS).toLong()

    companion object {
        fun now() = Time(getTimeNanos())
        fun nanoTime() = getTimeNanos()
        fun currentTimeMillis(): Long = getCurrentTimeMillis()
    }

    fun minusMillis(millis: Long) = Time(markNanos - millis * 1000)
    fun toSystemMilli() = markMillis
    fun isAfter(timeMark: Time): Boolean = this.markNanos > timeMark.markNanos
    fun isBefore(timeMark: Time): Boolean = this.markNanos < timeMark.markNanos
    fun elapsedSinceNow() = (this.markNanos - now().markNanos).toDuration(DurationUnit.NANOSECONDS)

    operator fun minus(other: Time) = (this.markNanos - other.markNanos).toDuration(DurationUnit.NANOSECONDS)

    override fun toString(): String {
        return markNanos.toDuration(DurationUnit.NANOSECONDS).toComponents { hours, minutes, seconds, nanos ->
            val millis =
                Duration.convert(
                    nanos.toDouble(),
                    DurationUnit.NANOSECONDS,
                    DurationUnit.MILLISECONDS
                )
                    .toLong()
                    .toString().padStart(3, '0')
            "$hours:$minutes:$seconds.$millis"
        }
    }
}

class SimpleCalendar(
    var day: Int,
    var month: Int,
    var year: Int,
    var hour: Int,
    var min: Int,
    var sec: Int,
    var tz: Int = 0,
) {

    companion object {
        fun fromEpochMilli(epochMilli: Long): SimpleCalendar {
            return getTimeForEpochMilli(epochMilli)
        }
        fun now(): SimpleCalendar {
            val now = getCurrentTimeMillis()
            return getTimeForEpochMilli(now)
        }
    }

    val epochTime
        get() = getEpochMilliForTime(day, month, year, hour, min, sec, tz)
}

internal expect fun getTimeNanos(): Long
internal expect fun getCurrentTimeMillis(): Long
internal expect fun getEpochMilliForTime(day: Int, month: Int, year: Int, hour: Int, min: Int, sec: Int, tz: Int): Long
internal expect fun getTimeForEpochMilli(epochMilli: Long): SimpleCalendar

expect object Sleep {
    fun blockFor(timeMillis: Long)
    fun blockFor(millis: Long, nanos: Int)

    @OptIn(ExperimentalTime::class)
    fun blockFor(duration: Duration)
}