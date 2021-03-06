package com.robgulley.time

import kotlin.math.abs
import kotlin.time.Duration
import kotlin.time.DurationUnit
import kotlin.time.ExperimentalTime
import kotlin.time.toDuration

@OptIn(ExperimentalTime::class)
actual class Time private actual constructor(private val markNanos: Long) {

    actual companion object {
        actual fun now() = Time(System.nanoTime())
        actual fun Time.between(other: Time): Duration =
            abs(this.markNanos - other.markNanos).toDuration(DurationUnit.NANOSECONDS)
    }

    actual val epochMilli: Long = getEpochTimeMillisNow()
    actual val nanoTime: Long = markNanos

    actual fun minusMillis(millis: Long) = Time(markNanos - millis * 1000)
    actual fun plusMillis(millis: Long) = Time(markNanos + millis * 1000)

    actual fun isAfter(timeMark: Time): Boolean = this.markNanos > timeMark.markNanos
    actual fun isBefore(timeMark: Time): Boolean = this.markNanos < timeMark.markNanos

    actual fun elapsedThenToNow(): Duration = (this.markNanos - now().markNanos).toDuration(DurationUnit.NANOSECONDS)


    actual operator fun minus(other: Duration): Time = Time(this.markNanos - other.inWholeNanoseconds)
    actual operator fun plus(other: Duration): Time = Time(this.markNanos + other.inWholeNanoseconds)

    actual override fun toString(): String {
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

    private fun getEpochTimeMillisNow(): Long = System.currentTimeMillis()

}