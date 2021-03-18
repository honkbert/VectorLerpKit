package com.robgulley

import kotlin.test.Test
import kotlin.test.assertTrue

class InterpolatedMapTest {

    private val sourceMap = mapOf(
        -10.0 to 4.0,
        0.0 to 2.0,
        10.0 to -2.0
    )

    @Test
    fun testMap() {
        val testMap = sourceMap.toInterpolatedMap()
        val answerMap = mapOf(
            -10.0 to 4.0,
            -9.8 to 3.96,
            -9.6 to 3.92,
            -9.4 to 3.88,
            -9.2 to 3.84,
            -9.0 to 3.8,
            -8.8 to 3.7600000000000002,
            -8.6 to 3.7199999999999998,
            -8.4 to 3.68,
            -8.2 to 3.6399999999999997,
            -8.0 to 3.6,
            -7.8 to 3.56,
            -7.6 to 3.52,
            -7.4 to 3.48,
            -7.2 to 3.44,
            -7.0 to 3.4,
            -6.8 to 3.36,
            -6.6 to 3.32,
            -6.4 to 3.2800000000000002,
            -6.2 to 3.24,
            -6.0 to 3.2,
            -5.8 to 3.16,
            -5.6 to 3.12,
            -5.4 to 3.08,
            -5.2 to 3.04,
            -5.0 to 3.0,
            -4.8 to 2.96,
            -4.6 to 2.92,
            -4.4 to 2.88,
            -4.2 to 2.84,
            -4.0 to 2.8,
            -3.8 to 2.76,
            -3.6 to 2.7199999999999998,
            -3.4 to 2.68,
            -3.2 to 2.64,
            -3.0 to 2.6,
            -2.8 to 2.56,
            -2.6 to 2.52,
            -2.4 to 2.48,
            -2.2 to 2.44,
            -2.0 to 2.4,
            -1.8 to 2.3600000000000003,
            -1.6 to 2.32,
            -1.4 to 2.2800000000000002,
            -1.2 to 2.2399999999999998,
            -1.0 to 2.2,
            -0.8 to 2.16,
            -0.6 to 2.12,
            -0.4 to 2.08,
            -0.2 to 2.04,
            0.0 to 2.0,
            0.2 to 1.92,
            0.4 to 1.84,
            0.6 to 1.76,
            0.8 to 1.68,
            1.0 to 1.6,
            1.2 to 1.52,
            1.4 to 1.44,
            1.6 to 1.3599999999999999,
            1.8 to 1.28,
            2.0 to 1.2,
            2.2 to 1.1199999999999999,
            2.4 to 1.04,
            2.6 to 0.96,
            2.8 to 0.8800000000000001,
            3.0 to 0.8,
            3.2 to 0.72,
            3.4 to 0.6400000000000001,
            3.6 to 0.56,
            3.8 to 0.48,
            4.0 to 0.3999999999999999,
            4.2 to 0.31999999999999984,
            4.4 to 0.23999999999999977,
            4.6 to 0.16000000000000014,
            4.8 to 0.08000000000000007,
            5.0 to 0.0,
            5.2 to -0.08000000000000007,
            5.4 to -0.16000000000000014,
            5.6 to -0.23999999999999977,
            5.8 to -0.31999999999999984,
            6.0 to -0.3999999999999999,
            6.2 to -0.48,
            6.4 to -0.56,
            6.6 to -0.6399999999999997,
            6.8 to -0.7199999999999998,
            7.0 to -0.7999999999999998,
            7.2 to -0.8799999999999999,
            7.4 to -0.96,
            7.6 to -1.04,
            7.8 to -1.12,
            8.0 to -1.2000000000000002,
            8.2 to -1.2799999999999998,
            8.4 to -1.3600000000000003,
            8.6 to -1.44,
            8.8 to -1.5200000000000005,
            9.0 to -1.6,
            9.2 to -1.6799999999999997,
            9.4 to -1.7600000000000002,
            9.6 to -1.8399999999999999,
            9.8 to -1.9200000000000004,
            10.0 to -2.0
        )

        val keys = IntProgression.fromClosedRange(-50, 50, 1).map { i -> i / 5.0 }
        val resultMap = testMap.generateMapFromKeys(keys)
        assertSortedMapsAreEqual(answerMap, resultMap, "Maps do not match")
    }
}

fun <K, V> assertSortedMapsAreEqual(expected: Map<K, V>, actual: Map<K, V>, message: String = "") {
    assertTrue(check(expected, actual), message)
}

private fun <K, V> check(expected: Map<K, V>, actual: Map<K, V>): Boolean {
    if (expected.keys.size != actual.keys.size) return false
    val expectedKeys = expected.keys.toList()
    val actualKeys = actual.keys.toList()
    for (i in expectedKeys.indices) {
        if (expectedKeys[i] != actualKeys[i]) return false
    }
    if (expected.values.size != actual.values.size) return false
    val expectedValues = expected.values.toList()
    val actualValues = actual.values.toList()
    for (i in expectedValues.indices) {
        if (expectedValues[i] != actualValues[i]) return false
    }
    return true
}