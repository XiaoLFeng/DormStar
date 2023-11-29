package com.xlf.dromstarkotlin.entity.doData

import java.sql.Timestamp

data class TokenDO(
    var id: Long?,
    var userId: Long?,
    var token: String?,
    var userAgent: String?,
    var ip: String?,
    var createdAt: Timestamp?,
    var updatedAt: Timestamp?
)
