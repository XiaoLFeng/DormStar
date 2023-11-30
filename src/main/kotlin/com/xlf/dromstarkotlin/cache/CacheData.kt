package com.xlf.dromstarkotlin.cache

object CacheData {
    val tokenVisits = HashMap<String, CacheToken>()
    var autoLogin = false
    var allowRegister = false
    const val LOGIN_METHOD = "/eportal/portal/login?callback=dr1003&login_method=1&lang=zh-cn&v=5836"
    const val LOGOUT_METHOD = "/eportal/portal/mac/unbind?callback=dr1002&lang=zh"
    const val STATUS_METHOD = "/drcom/chkstatus?callback=dr1002"
}

data class CacheToken(
    var count: Long = 0,
    val timestamp: Long,
)