package com.xlf.dromstarkotlin.entity.doData

import java.sql.Timestamp

data class UserDO(
    var id: Long?,
    var user: String?,
    var password: String?,
    var email: String?,
    var tel: String?,
    var permission: Short?,
    var createdAt: Timestamp?,
    var updatedAt: Timestamp?
)
