package com.robgulley

import com.robgulley.range.by
import com.robgulley.range.linject

class InterpolatedMap(private val map: SortedMap<Double, Double>) : Map<Double, Double> by map {

    private val absMin: Double = map.firstKey()
    private val absMax: Double = map.lastKey()

    override fun get(key: Double): Double? =
        when {
            key <= absMin -> map[map.keys.first()]
            key >= absMax -> map[map.keys.last()]
            else -> {
                val listKeys = map.keys.toList()
                var result = 0.0
                listKeys.zipWithNext().forEach {
                    if (key in it.first..it.second) {
                        val keyRange = it.first..it.second
                        val valueRange = map.getValue(it.first)..map.getValue(it.second)
                        result = keyRange linject valueRange by key
                    }
                }
                result
            }
        }

    fun generateMapFromKeys(keys: List<Double>, defaultValue: Double = Double.NaN): SortedMap<Double, Double> =
        keys.sorted().associateWith { key -> getOrElse(key) { defaultValue } }.toSortedMap()

}

fun Map<Double, Double>.toInterpolatedMap() = InterpolatedMap(this.toSortedMap())