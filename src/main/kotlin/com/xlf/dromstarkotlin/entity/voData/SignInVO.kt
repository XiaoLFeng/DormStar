package com.xlf.dromstarkotlin.entity.voData

/**
 * 用户登录信息自定义实体类
 *
 * @author 筱锋xiao_lfeng
 * @since v1.0.0
 */
data class SignInVO (
    val action: String,
    val username: String,
    val password: String,
    val timestamp: Long,
)