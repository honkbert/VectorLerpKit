package com.robgulley.range

import com.robgulley.vector.Vector
import com.robgulley.vector.VectorInt
import kotlin.math.roundToInt
import kotlin.math.roundToLong

sealed class LinearProjection<A : Comparable<A>, B : Comparable<B>> {
    protected abstract val inRange: ClosedRange<A>
    protected abstract val outRange: ClosedRange<B>
    abstract val project: (A) -> B
    val projectAndCoerce: (A) -> B = { a: A -> project(a).coerceIn(outRange) }
    abstract fun invert(): LinearProjection<B, A>
    abstract infix operator fun invoke(value: A): B
}

private class LinearProjectionDoubleDouble(
    override val inRange: ClosedRange<Double>,
    override val outRange: ClosedRange<Double>
) : LinearProjection<Double, Double>() {
    override val project = { input: Double -> (input from inRange) * outRange.span() + outRange.start }
    override infix operator fun invoke(value: Double) = project(value)
    override fun invert() = LinearProjectionDoubleDouble(outRange, inRange)
}

private class LinearProjectionDoubleInt(
    override val inRange: ClosedRange<Double>,
    override val outRange: ClosedRange<Int>
) : LinearProjection<Double, Int>() {
    override val project = { input: Double -> ((input from inRange) * outRange.span()).roundToInt() + outRange.start }
    override infix operator fun invoke(value: Double) = project(value)
    override fun invert() = LinearProjectionIntDouble(outRange, inRange)
}

private class LinearProjectionDoubleLong(
    override val inRange: ClosedRange<Double>,
    override val outRange: ClosedRange<Long>
) : LinearProjection<Double, Long>() {
    override val project = { input: Double -> ((input from inRange) * outRange.span()).roundToLong() + outRange.start }
    override infix operator fun invoke(value: Double) = project(value)
    override fun invert() = LinearProjectionLongDouble(outRange, inRange)
}


private class LinearProjectionIntDouble(
    override val inRange: ClosedRange<Int>,
    override val outRange: ClosedRange<Double>
) : LinearProjection<Int, Double>() {
    override val project = { input: Int -> (input from inRange) * outRange.span() + outRange.start }
    override infix operator fun invoke(value: Int) = project(value)
    override fun invert() = LinearProjectionDoubleInt(outRange, inRange)
}

private class LinearProjectionLongDouble(
    override val inRange: ClosedRange<Long>,
    override val outRange: ClosedRange<Double>
) : LinearProjection<Long, Double>() {
    override val project = { input: Long -> (input from inRange) * outRange.span() + outRange.start }
    override infix operator fun invoke(value: Long) = project(value)
    override fun invert() = LinearProjectionDoubleLong(outRange, inRange)
}

private class LinearProjectionIntInt(override val inRange: ClosedRange<Int>, override val outRange: ClosedRange<Int>) :
    LinearProjection<Int, Int>() {
    override val project =
        { input: Int -> ((input from inRange) * outRange.span()).roundToInt() + outRange.start }

    override infix operator fun invoke(value: Int) = project(value)
    override fun invert() = LinearProjectionIntInt(outRange, inRange)
}

private class LinearProjectionLongLong(
    override val inRange: ClosedRange<Long>,
    override val outRange: ClosedRange<Long>
) :
    LinearProjection<Long, Long>() {
    override val project =
        { input: Long -> ((input from inRange) * outRange.span()).roundToLong() + outRange.start }

    override infix operator fun invoke(value: Long) = project(value)
    override fun invert() = LinearProjectionLongLong(outRange, inRange)
}

infix fun IntRange.linject(targetRange: IntRange): LinearProjection<Int, Int> =
    LinearProjectionIntInt(this, targetRange)

infix fun IntRange.linject(targetRange: ClosedRange<Double>): LinearProjection<Int, Double> =
    LinearProjectionIntDouble(this, targetRange)

infix fun LongRange.linject(targetRange: ClosedRange<Double>): LinearProjection<Long, Double> =
    LinearProjectionLongDouble(this, targetRange)

infix fun LongRange.linject(targetRange: LongRange): LinearProjection<Long, Long> =
    LinearProjectionLongLong(this, targetRange)

infix fun ClosedRange<Double>.linject(targetRange: IntRange): LinearProjection<Double, Int> =
    LinearProjectionDoubleInt(this, targetRange)

infix fun ClosedRange<Double>.linject(targetRange: ClosedRange<Double>): LinearProjection<Double, Double> =
    LinearProjectionDoubleDouble(this, targetRange)

infix fun ClosedRange<Double>.linject(targetRange: LongRange): LinearProjection<Double, Long> =
    LinearProjectionDoubleLong(this, targetRange)

fun lerp(a: Double, b: Double, by: Double): Double = (b - a) * by + a
fun lerp(a: Int, b: Int, by: Double): Int = ((b - a) * by + a).roundToInt()
fun lerp(a: Long, b: Long, by: Double): Long = ((b - a) * by + a).roundToLong()
fun lerp(a: Vector, b: Vector, by: Double) = Vector(a.list.zip(b.list).map { lerp(it.first, it.second, by) })
fun lerp(a: VectorInt, b: VectorInt, by: Double) = VectorInt(a.list.zip(b.list).map { lerp(it.first, it.second, by) })
fun lerp(range: ClosedRange<Double>, by: Double) = lerp(range.start, range.endInclusive, by)
fun lerp(range: IntRange, by: Double) = lerp(range.first, range.last, by)
fun lerp(range: LongRange, by: Double) = lerp(range.first, range.last, by)