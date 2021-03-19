package com.robgulley.range

import kotlin.math.abs
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class ProjectionTest {

    @Test
    fun testIntInt() {
        val projection = 1..10 projectFun 1..20
        val project = projection.project
        assertEquals(-1, project(0))
        assertEquals(1, project(1))
        assertEquals(7, project(4))
        assertEquals(9, project(5))
        assertEquals(12, project(6))
        assertEquals(20, project(10))
    }

    @Test
    fun testIntInt2() {
        val projection = 0..10 projectFun 0..20
        val project = projection.project
        assertEquals(0, project(0))
        assertEquals(8, project(4))
        assertEquals(10, project(5))
        assertEquals(12, project(6))
        assertEquals(20, project(10))
    }

    @Test
    fun testDoubleDouble() {
        val project = (0.0..10.0 projectFun 0.0..20.0).project
        assertEquals(-1.0, project(-0.5), 0.0)
        assertEquals(0.0, project(0.0), 0.0)
        assertEquals(10.0, project(5.0), 0.0)
        assertEquals(20.0, project(10.0), 0.0)
        assertEquals(4040.0, project(2020.0), 0.0)
    }

    @Test
    fun testDoubleInt() {
        val project = (0.0..10.0 projectFun 0..20).project
        assertEquals(-1, project(-0.6))
        assertEquals(-1, project(-0.5))
        assertEquals(-1, project(-0.26))
        assertEquals(0, project(-0.25))
        assertEquals(0, project(-0.24))
        assertEquals(0, project(0.0))
        assertEquals(10, project(5.0))
        assertEquals(20, project(10.0))
        assertEquals(4040, project(2020.0))
    }


}

/**
 * Asserts that two doubles are equal to within a positive delta.
 * If they are not, an {@link AssertionError} is thrown with the given
 * message. If the expected value is infinity then the delta value is
 * ignored. NaNs are considered equal:
 * <code>assertEquals(Double.NaN, Double.NaN, *)</code> passes
 *
 * @param expected expected value
 * @param actual the value to check against <code>expected</code>
 * @param delta the maximum delta between <code>expected</code> and
 * <code>actual</code> for which both numbers are still
 * considered equal.
 */
fun assertEquals(expected: Double, actual: Double, delta: Double) {
    if (doubleIsDifferent(expected, actual, delta)) {
        assertNotEquals(expected, actual)
    }
}

private fun doubleIsDifferent(d1: Double, d2: Double, delta: Double): Boolean {
    if (d1.compareTo(d2) == 0) {
        return false
    }
    return abs(d1 - d2) > delta
}