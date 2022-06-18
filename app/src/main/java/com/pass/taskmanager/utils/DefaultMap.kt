package com.pass.taskmanager.utils

class DefaultMap<K, V>(private val map: MutableMap<K, V>, private val factory: (K) -> V) :
    MutableMap<K, V> by map {

    override fun get(key: K): V {
        return map.getOrPut(key) { factory(key) }
    }
}
