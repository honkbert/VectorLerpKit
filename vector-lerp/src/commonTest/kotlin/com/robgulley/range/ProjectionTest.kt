package com.robgulley.range

import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals

class ProjectionTest {

    @Test
    fun testIntToInt() {
        val project = (1..10 linject 1..20).project
        assertEquals(-1, project(0))
        assertEquals(1, project(1))
        assertEquals(7, project(4))
        assertEquals(9, project(5))
        assertEquals(12, project(6))
        assertEquals(20, project(10))
    }

    @Test
    fun testIntToInt2() {
        val project = (0..10 linject 0..20).project
        assertEquals(0, project(0))
        assertEquals(8, project(4))
        assertEquals(10, project(5))
        assertEquals(12, project(6))
        assertEquals(20, project(10))
    }

    @Test
    fun testIntToDouble() {
        val project = (0..10 linject 3.0..7.0)
        val results: Map<Int, Double> = mapOf(
            0 to 3.0,
            1 to 3.4,
            10 to 7.0,
            11 to 7.4
        )
        assertContentEquals(results.values, results.keys.map { project(it) })
    }

    @Test
    fun testLongToLong() {
        val project = (1L..10 linject 1L..20).project
        assertEquals(-1, project(0))
        assertEquals(1, project(1))
        assertEquals(7, project(4))
        assertEquals(9, project(5))
        assertEquals(12, project(6))
        assertEquals(20, project(10))
    }

    @Test
    fun testLongToLong2() {
        val project = (0L..10 linject 0L..20).project
        assertEquals(0, project(0))
        assertEquals(8, project(4))
        assertEquals(10, project(5))
        assertEquals(12, project(6))
        assertEquals(20, project(10))
    }

    @Test
    fun testLongToDouble() {
        val project = (0L..10L linject 3.0..7.0)
        val results: Map<Long, Double> = mapOf(
            0L to 3.0,
            1L to 3.4,
            10L to 7.0,
            11L to 7.4
        )
        assertContentEquals(results.values, results.keys.map { project(it) })
    }

    @Test
    fun testDoubleToDouble() {
        val project = (0.0..10.0 linject 0.0..20.0).project
        assertEquals(-1.0, project(-0.5), 0.0)
        assertEquals(0.0, project(0.0), 0.0)
        assertEquals(10.0, project(5.0), 0.0)
        assertEquals(20.0, project(10.0), 0.0)
        assertEquals(4040.0, project(2020.0), 0.0)
    }

    @Test
    fun testDoubleToInt() {
        val project = (0.0..10.0 linject 0..20).project
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

    @Test
    fun testDoubleToLong() {
        val project = (0.0..10.0 linject 0L..20L).project
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