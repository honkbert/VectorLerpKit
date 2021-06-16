package com.robgulley

import com.robgulley.range.from
import com.robgulley.range.projectInto

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
                    if (key in it.run { first..second }) {
                        val keyRange = it.run { first..second }
                        val valueRange = map.getValue(it.first)..map.getValue(it.second)
                        result = key from keyRange projectInto valueRange
                    }
                }
                result
            }
        }

    fun isKey(key: Double) = map.containsKey(key)

    fun getValueAndIsKey(key: Double): Pair<Double?, Boolean> = Pair(get(key), isKey(key))

    fun generateMapFromKeys(keys: List<Double>, defaultValue: Double = Double.NaN): SortedMap<Double, Double> =
        keys.sorted().associateWith { key -> getOrDefault(key, defaultValue) }.toSortedMap()


    companion object {
        fun toInterpolatedMap(map: Map<Double, Double>) = InterpolatedMap(map.toSortedMap())
    }
}

fun Map<Double, Double>.toInterpolatedMap() = InterpolatedMap(this.toSortedMap())
fun <K,V> Map<K,V>.getOrDefault(key: K, defaultValue: V) = this.getOrElse(key) { defaultValue }
