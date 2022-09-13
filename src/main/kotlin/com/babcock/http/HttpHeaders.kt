package com.babcock.http

import java.util.*

class HttpHeaders(mapOfHeaders: Map<String, Collection<String>> = emptyMap()) {

    private val mapOfHeaders: Map<String, Collection<String>> = mapOfHeaders.mapKeys { (k, _) -> k.lowercase(Locale.getDefault()) } //1

    constructor(vararg pairs: Pair<String, String>)
            : this(pairs.asSequence()
        .groupBy { (name, _) -> name } // 2
        .mapValues { (_, namesWithValues) -> namesWithValues.map { (_, values) -> values } }
        .toMap())

    val size = this.mapOfHeaders.size

    val contentLength: Int = this["content-length"].firstOrNull()?.toInt() ?: 0

    fun asSequence() = mapOfHeaders.asSequence()
    operator fun get(key: String): Collection<String> = mapOfHeaders[key.lowercase(Locale.getDefault())] ?: emptyList()
    operator fun plus(pair: Pair<String, String>) = HttpHeaders(mapOfHeaders + (pair.first to listOf(pair.second)))
}
