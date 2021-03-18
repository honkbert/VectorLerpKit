package com.robgulley

class SortedMap<K : Comparable<K>, V>(private val map: Map<K, V>) : Map<K, V> {
    fun firstKey(): K = entries.first().key
    fun lastKey(): K = entries.last().key

    override val entries: Set<Map.Entry<K, V>>
        get() = map.entries.sortedBy { it.key }.toSet()

    override val keys: Set<K>
        get() = map.keys
    override val size: Int
        get() = map.size
    override val values: Collection<V>
        get() = map.values

    override fun containsKey(key: K): Boolean = map.containsKey(key)

    override fun containsValue(value: V) = map.containsValue(value)

    override fun get(key: K): V? = map[key]

    override fun isEmpty(): Boolean = map.isEmpty()
}

fun <K : Comparable<K>, V> Map<K, V>.toSortedMap() = SortedMap(this)