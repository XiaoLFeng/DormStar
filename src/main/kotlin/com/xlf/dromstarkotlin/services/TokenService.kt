package com.xlf.dromstarkotlin.services

import com.xlf.dromstarkotlin.entity.doData.TokenDO
import com.xlf.dromstarkotlin.mapper.TokenMapper
import com.xlf.dromstarkotlin.utils.Processing
import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.sql.Timestamp
import java.util.Date

/**
 * Token 控制器
 *
 * 对本系统的进行授权操作
 *
 * @since v1.0.0
 * @author 筱锋xiao_lfeng
 */
@Service
class TokenService(
    private val tokenMapper: TokenMapper
) {
    /**
     * 对 token 进行校验
     */
    @Transactional
    fun tokenVerify(token: String, httpServletResponse: HttpServletResponse): Boolean {
        val tokenDO = tokenMapper.getToken(token)
        // 判断 token 是否存在
        if (tokenDO != null) {
            // 已登录用户
            if (tokenDO.userId != null) {
                // 判断 token 是否过期
                if (tokenDO.updatedAt != null) {
                    if (tokenDO.updatedAt!!.time + 43200000 > Date().time) {
                        return true
                    } else {
                        tokenMapper.deleteToken(token)
                        val cookie = Cookie("session", null).also { it.maxAge = 0 }.also { it.path = "/" }
                        httpServletResponse.addCookie(cookie)
                    }
                } else {
                    if (tokenDO.createdAt!!.time + 43200000 > Date().time) {
                        return true
                    } else {
                        tokenMapper.deleteToken(token)
                        val cookie = Cookie("session", null).also { it.maxAge = 0 }.also { it.path = "/" }
                        httpServletResponse.addCookie(cookie)
                    }
                }
            } else {
                // 未登录用户
                if (tokenDO.createdAt!!.time + 300000 > Date().time) {
                    return true
                } else {
                    tokenMapper.deleteToken(token)
                    val cookie = Cookie("session", null).also { it.maxAge = 0 }.also { it.path = "/" }
                    httpServletResponse.addCookie(cookie)
                }
            }
        }
        return false
    }

    /**
     * 创建 token
     */
    @Transactional
    fun tokenCreate(httpServletRequest: HttpServletRequest): String {
        var token: String
        var tokenDO: TokenDO?
        do {
            // 创建 token
            token = Processing.createToken()
            // 检查 token 是否存在
            tokenDO = tokenMapper.getToken(token)
        } while (tokenDO != null)
        // 不存在则创建
        val newTokenDO = TokenDO(
            null,
            null,
            token,
            httpServletRequest.getHeader("User-Agent"),
            httpServletRequest.localAddr,
            Timestamp(Date().time),
            null
        )
        tokenMapper.insertToken(newTokenDO)
        return token
    }
}