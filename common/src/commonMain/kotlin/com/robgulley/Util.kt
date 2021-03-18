package com.robgulley

import kotlin.math.roundToInt

internal fun Double.format(digits: Int): String {
    var dotAt = 1
    repeat(digits) { dotAt *= 10 }
    val roundedValue = (this * dotAt).roundToInt()
    return ((roundedValue / dotAt) + (roundedValue % dotAt).toFloat() / dotAt).toString()
}