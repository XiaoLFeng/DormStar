package com.xlf.dromstarkotlin.entity.doData

import java.sql.Timestamp

data class AccountDO(
    var id: Long?,
    var userId: Long?,
    var user: String?,
    var password: String?,
    var type: String?,
    var createTime: Timestamp?,
    var updateTime: Timestamp?
)