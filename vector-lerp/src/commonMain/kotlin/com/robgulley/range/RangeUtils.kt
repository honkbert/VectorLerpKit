package com.robgulley.range

private fun spanOrNonZeroDouble(range: ClosedRange<Double>): Double =
    if (range.span() == 0.0) Double.MIN_VALUE else range.span()

private fun spanOrNonZeroInt(range: ClosedRange<Int>): Double =
    if (range.span() == 0) Double.MIN_VALUE else range.span().toDouble()

private fun spanOrNonZeroLong(range: ClosedRange<Long>): Double =
    if (range.span() == 0L) Double.MIN_VALUE else range.span().toDouble()

infix fun Double.from(doubleRange: ClosedRange<Double>): Double =
    (this - doubleRange.start) / spanOrNonZeroDouble(doubleRange)

infix fun Int.from(intRange: ClosedRange<Int>): Double = (this - intRange.start) / spanOrNonZeroInt(intRange)
infix fun Long.from(longRange: ClosedRange<Long>): Double = (this - longRange.start) / spanOrNonZeroLong(longRange)

infix fun <A : Comparable<A>, B : Comparable<B>> LinearProjection<A, B>.by(amount: A) = this.invoke(amount)

infix fun ClosedRange<Double>.lerpBy(by: Double) = lerp(start, endInclusive, by)
infix fun ClosedRange<Int>.lerpBy(by: Double) = lerp(start, endInclusive, by)
infix fun ClosedRange<Long>.lerpBy(by: Double) = lerp(start, endInclusive, by)

fun ClosedRange<Double>.span(): Double = this.endInclusive - this.start
fun ClosedRange<Int>.span() = this.endInclusive - this.start
fun ClosedRange<Long>.span() = this.endInclusive - this.start

fun <T : Comparable<T>> ClosedRange<T>.invert() = this.endInclusive..this.start

fun ClosedRange<Double>.toFloat() = this.start.toFloat()..this.endInclusive.toFloat()
fun ClosedRange<Float>.toDouble() = this.start.toDouble()..this.endInclusive.toDouble()