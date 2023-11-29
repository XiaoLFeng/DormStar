package com.xlf.dromstarkotlin.controllers

import com.frontleaves.general.utils.BaseResponse
import com.frontleaves.general.utils.ResultUtil
import com.xlf.dromstarkotlin.services.TokenService
import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CookieValue
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/token")
@CrossOrigin(origins = ["*"])
class TokenController(
    private val tokenService: TokenService
) {
    /**
     * 创建 Token 组件
     */
    @PostMapping("/create")
    fun createToken(
        @CookieValue("session") token: String?, httpServletResponse: HttpServletResponse,
        @RequestParam("return") returnLink: String?,
        httpServletRequest: HttpServletRequest
    ): ResponseEntity<BaseResponse> {
        // 检查 token 是否存在
        return if (token == null) {
            val newToken = tokenService.tokenCreate(httpServletRequest)
            httpServletResponse.addCookie(
                Cookie("session", newToken)
                    .also { it.path = "/" }
                    .also { it.maxAge = 43200 }
            )
            ResultUtil.redirect("SuccessCreate", "Token创建成功", newToken, returnLink, httpServletRequest)
        } else {
            if (tokenService.tokenVerify(token, httpServletResponse)) {
                ResultUtil.redirect("StillValid", "Token依旧有效", null, returnLink, httpServletRequest)
            } else {
                httpServletResponse.addCookie(
                    Cookie("session", null)
                        .also { it.path = "/" }
                        .also { it.maxAge = 0 }
                )
                this.createToken(null, httpServletResponse, returnLink, httpServletRequest)
            }
        }
    }
}