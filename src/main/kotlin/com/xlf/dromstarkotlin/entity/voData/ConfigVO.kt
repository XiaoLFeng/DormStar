package com.xlf.dromstarkotlin.entity.voData

data class ConfigVO(
    val mysql: MysqlVO
)

data class MysqlVO(
    val host: String,
    val port: Int,
    val username: String,
    val password: String,
    val database: String
)
