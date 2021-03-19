package com.robgulley.range

import kotlin.math.roundToInt

sealed class Projection<A : Comparable<A>, B : Comparable<B>> {
    protected abstract val inRange: ClosedRange<A>
    protected abstract val outRange: ClosedRange<B>
    abstract val project: (A) -> B
    val projectAndCoerce: (A) -> B = { a: A -> project(a).coerceIn(outRange) }
    abstract fun invert(): Projection<B, A>
    abstract infix operator fun invoke(value: A): B
}

private class ProjectionDoubleDouble(override val inRange: ClosedRange<Double>, override val outRange: ClosedRange<Double>) : Projection<Double, Double>() {
    override val project = { input: Double -> (input from inRange) * outRange.span() + outRange.start }
    override infix operator fun invoke(value: Double) = project(value)
    override fun invert() = ProjectionDoubleDouble(outRange, inRange)
}

private class ProjectionDoubleInt(override val inRange: ClosedRange<Double>, override val outRange: ClosedRange<Int>) : Projection<Double, Int>() {
    override val project = { input: Double -> ((input from inRange) * outRange.span()).roundToInt() + outRange.start }
    override infix operator fun invoke(value: Double) = project(value)
    override fun invert() = ProjectionIntDouble(outRange, inRange)
}

private class ProjectionIntDouble(override val inRange: ClosedRange<Int>, override val outRange: ClosedRange<Double>) : Projection<Int, Double>() {
    override val project = { input: Int -> (input from (inRange as IntRange)) * outRange.span() + outRange.start }
    override infix operator fun invoke(value: Int) = project(value)
    override fun invert() = ProjectionDoubleInt(outRange, inRange)
}

private class ProjectionIntInt(override val inRange: ClosedRange<Int>, override val outRange: ClosedRange<Int>) : Projection<Int, Int>() {
    override val project = { input: Int -> ((input from (inRange as IntRange)) * outRange.span()).roundToInt() + outRange.start }
    override infix operator fun invoke(value: Int) = project(value)
    override fun invert() = ProjectionIntInt(outRange, inRange)
}

infix fun IntRange.projectFun(targetRange: IntRange): Projection<Int, Int> = ProjectionIntInt(this, targetRange)
infix fun IntRange.projectFun(targetRange: ClosedRange<Double>): Projection<Int, Double> = ProjectionIntDouble(this, targetRange)
infix fun ClosedRange<Double>.projectFun(targetRange: IntRange): Projection<Double, Int> = ProjectionDoubleInt(this, targetRange)
infix fun ClosedRange<Double>.projectFun(targetRange: ClosedRange<Double>): Projection<Double, Double> = ProjectionDoubleDouble(this, targetRange)