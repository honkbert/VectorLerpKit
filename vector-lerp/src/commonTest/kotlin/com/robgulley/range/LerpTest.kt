package com.robgulley.range

import com.robgulley.vector.Vector
import com.robgulley.vector.VectorInt
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals

class LerpTest {
    @Test
    fun testBasicLerp() {
        assertEquals(
            expected = 3.0,
            actual = lerp(a = 2.0, b = 4.0, by = 0.5)
        )
    }

    @Test
    fun testVectorLerp() {
        assertContentEquals(
            expected = Vector(-1.0, 0.0, 1.0).array,
            actual = lerp(
                a = Vector(-2.0, -1.0, 2.0),
                b = Vector(-0.0, 1.0, 0.0),
                by = 0.5
            ).array,
        )
    }

    @Test
    fun testVectorIntLerp() {
        assertContentEquals(
            expected = VectorInt(-1, 0, 1).array,
            actual = lerp(
                a = VectorInt(-2, -1, 2),
                b = VectorInt(-0, 1, 0),
                by = 0.5
            ).array,
        )
    }
}