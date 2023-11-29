package com.xlf.dromstarkotlin.entity.voData

/**
 * 注册用户信息自定义实体类
 *
 * @author 筱锋xiao_lfeng
 * @since v1.0.0
 */
data class SignUpVO (
    val action: String,
    val username: String,
    val password: String,
    val email: String,
    val telephone: String?,
    val timestamp: Long,
)
