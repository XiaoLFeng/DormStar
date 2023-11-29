package com.xlf.dromstarkotlin.entity.voData

/**
 * 用户登录信息自定义实体类
 *
 * @author 筱锋xiao_lfeng
 * @since v1.0.0
 */
data class SignInVO (
    var action: String? = null,
    var username: String? = null,
    var password: String? = null,
    var timestamp: Long? = null,
)