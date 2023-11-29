package com.xlf.dromstarkotlin.cache

object CacheData {
    val tokenVisits = HashMap<String, CacheToken>()
}

data class CacheToken(
    var count: Long = 0,
    val timestamp: Long,
)