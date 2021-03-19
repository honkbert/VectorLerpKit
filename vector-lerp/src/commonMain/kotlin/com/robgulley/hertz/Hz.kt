package com.robgulley.hertz

import kotlin.math.roundToLong

data class Hz(val frequency: Double) : Comparable<Hz> {
    constructor(frequency: Int) : this(frequency.toDouble())
    constructor(frequency: Long) : this(frequency.toDouble())

    val microseconds: Long = ((1 / frequency) * 1000000).roundToLong()
    val milliseconds: Long = ((1 / frequency) * 1000).roundToLong()

    val wavelength: Double = 1 / frequency

    override fun toString() = "$frequency Hz"

    override fun compareTo(other: Hz) = frequency.compareTo(other.frequency)

    infix operator fun times(other: Double) = Hz(frequency * other)
    infix operator fun minus(other: Double) = Hz(frequency - other)
    infix operator fun plus(other: Double) = Hz(frequency + other)
    infix operator fun div(other: Double) = Hz(frequency / other)

    infix operator fun times(other: Long) = Hz(frequency * other)
    infix operator fun minus(other: Long) = Hz(frequency - other)
    infix operator fun plus(other: Long) = Hz(frequency + other)
    infix operator fun div(other: Long) = Hz(frequency / other)

    infix operator fun times(other: Int) = Hz(frequency * other)
    infix operator fun minus(other: Int) = Hz(frequency - other)
    infix operator fun plus(other: Int) = Hz(frequency + other)
    infix operator fun div(other: Int) = Hz(frequency / other)
}