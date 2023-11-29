package com.xlf.dromstarkotlin.controllers

import com.frontleaves.general.utils.BaseResponse
import com.frontleaves.general.utils.ErrorCode
import com.frontleaves.general.utils.ResultUtil
import com.xlf.dromstarkotlin.entity.voData.SignInVO
import com.xlf.dromstarkotlin.exception.BusinessException
import com.xlf.dromstarkotlin.services.TokenService
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CookieValue
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * 用户控制器
 *
 * 对用户进行管理和操作
 *
 * @author 筱锋xiao_lfeng
 * @since v1.0.0
 */
@RestController
@RequestMapping("/api/user")
class UserController(
    private val tokenService: TokenService
) {
    /**
     * 用户登录组件
     */
    @PostMapping("/sign/in")
    fun signIn(
        signInVO: SignInVO?, @CookieValue("sessionId") token: String?, httpServletResponse: HttpServletResponse,
        httpServletRequest: HttpServletRequest
    ): ResponseEntity<BaseResponse>? {
        if (signInVO == null) {
            return BusinessException().backInfo(ErrorCode.MISSING_REQUEST_BODY, httpServletRequest)
        } else {
            // 对 Token 进行校验
            if (token != null) {
                // 对 token 进行校验
                if (!tokenService.tokenVerify(token, httpServletResponse)) {
                    // 校验失败
                    return BusinessException().backInfo(ErrorCode.TOKEN_VERIFY_FAILED, httpServletRequest)
                }
            } else {
                // 跳转至创建 Token 页面
                return ResultUtil.redirect("/api/token/create", httpServletRequest)
            }
        }
        return null
    }
}