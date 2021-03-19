package com.robgulley.range

import kotlin.math.roundToInt

infix fun ClosedRange<Double>.from(value: Double): Double = (value - this.start) / spanOrNonZero(this)
infix fun ClosedRange<Double>.from(value: Int): Double = (value - this.start) / spanOrNonZero(this)
infix fun IntRange.from(value: Int): Double = (value - this.first) / spanOrNonZero(this)
infix fun IntRange.from(value: Double): Double = (value - this.first) / spanOrNonZero(this)

private fun spanOrNonZero(range: ClosedRange<Double>): Double = range.span().let { span ->
    if (span == 0.0) Double.MIN_VALUE else span
}

private fun spanOrNonZero(range: IntRange): Double = range.span().let { span ->
    if (span == 0) Double.MIN_VALUE else span.toDouble()
}

infix fun Double.from(doubleRange: ClosedRange<Double>) = doubleRange.from(this)
infix fun Int.from(doubleRange: ClosedRange<Double>) = doubleRange.from(this)
infix fun Int.from(intRange: IntRange) = intRange.from(this)
infix fun Double.from(intRange: IntRange) = intRange.from(this)

infix fun Double.projectInto(intRange: IntRange) = (this * intRange.span()).roundToInt() + intRange.first
infix fun Double.projectInto(doubleRange: ClosedRange<Double>) = this * doubleRange.span() + doubleRange.start

fun ClosedRange<Double>.span(): Double = this.endInclusive - this.start
fun ClosedRange<Int>.span() = this.endInclusive - this.start

fun <T : Comparable<T>> ClosedRange<T>.invert() = this.endInclusive..this.start

fun ClosedRange<Double>.toFloat() = this.start.toFloat()..this.endInclusive.toFloat()
fun ClosedRange<Float>.toDouble() = this.start.toDouble()..this.endInclusive.toDouble()