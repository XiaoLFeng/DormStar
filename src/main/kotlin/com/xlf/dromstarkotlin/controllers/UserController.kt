package com.xlf.dromstarkotlin.controllers

import com.frontleaves.general.utils.BaseResponse
import com.frontleaves.general.utils.ErrorCode
import com.frontleaves.general.utils.ResultUtil
import com.xlf.dromstarkotlin.entity.voData.SignInVO
import com.xlf.dromstarkotlin.entity.voData.SignUpVO
import com.xlf.dromstarkotlin.exception.BusinessException
import com.xlf.dromstarkotlin.mapper.InfoMapper
import com.xlf.dromstarkotlin.services.TokenService
import com.xlf.dromstarkotlin.services.UserService
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CookieValue
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.Date

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
    private val tokenService: TokenService,
    private val userService: UserService,
    private val infoMapper: InfoMapper,
) {
    /**
     * 用户登录组件
     */
    @GetMapping("/sign/in")
    fun signIn(
        @RequestBody signInVO: SignInVO?, @CookieValue("session") token: String?, httpServletResponse: HttpServletResponse,
        httpServletRequest: HttpServletRequest
    ): ResponseEntity<BaseResponse> {
        // 判断请求体是否为空
        if (signInVO == null) {
            return BusinessException().backInfo(ErrorCode.MISSING_REQUEST_BODY, httpServletRequest)
        } else {
            // 判断时间戳
            if (signInVO.timestamp + 5000 < Date().time || signInVO.timestamp - 5000 > Date().time) {
                return ResultUtil.error(ErrorCode.TIMESTAMP_EXPIRED, httpServletRequest)
            }
            // 对 Token 进行校验
            return if (token != null) {
                // 对 token 进行校验
                if (!tokenService.tokenVerify(token, httpServletResponse)) {
                    // 校验失败
                    BusinessException().backInfo(ErrorCode.TOKEN_VERIFY_FAILED, httpServletRequest)
                } else {
                    // 用户登录操作
                    userService.signIn(signInVO, token, httpServletRequest)
                }
            } else {
                // 跳转至创建 Token 页面
                ResultUtil.redirect("/api/token/create", httpServletRequest)
            }
        }
    }

    /**
     * 用户注册组件
     */
    @GetMapping("/sign/up")
    fun signUp(
        @RequestBody signUpVO: SignUpVO?, @CookieValue("session") token: String?, httpServletResponse: HttpServletResponse,
        httpServletRequest: HttpServletRequest
    ): ResponseEntity<BaseResponse> {
        // 检查是否允许注册
        if (!infoMapper.getRegister()) {
            return ResultUtil.error(ErrorCode.REGISTRATION_NOT_ALLOWED, httpServletRequest)
        }
        // 判断请求体是否为空
        if (signUpVO == null) {
            return ResultUtil.error(ErrorCode.MISSING_REQUEST_BODY, httpServletRequest)
        } else {
            // 判断时间戳
            if (signUpVO.timestamp + 5000 < Date().time || signUpVO.timestamp - 5000 > Date().time) {
                return ResultUtil.error(ErrorCode.TIMESTAMP_EXPIRED, httpServletRequest)
            }
            // 对 Token 进行校验
            return if (token != null) {
                // 对 token 进行校验
                if (!tokenService.tokenVerify(token, httpServletResponse)) {
                    // 校验失败
                    BusinessException().backInfo(ErrorCode.TOKEN_VERIFY_FAILED, httpServletRequest)
                } else {
                    // 用户登录操作
                    userService.signUp(signUpVO, token, httpServletRequest)
                }
            } else {
                // 跳转至创建 Token 页面
                ResultUtil.redirect("/api/token/create", httpServletRequest)
            }
        }
    }
}