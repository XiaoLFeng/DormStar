package com.xlf.dromstarkotlin.controllers

import com.frontleaves.general.utils.BaseResponse
import com.frontleaves.general.utils.ErrorCode
import com.frontleaves.general.utils.ResultUtil
import com.xlf.dromstarkotlin.services.LogService
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CookieValue
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/log")
class LogController(
    val logService: LogService
) {

    @GetMapping("/auto-login")
    fun getAutoLoginLog(
        @CookieValue("session") token: String?,
        httpServletRequest: HttpServletRequest,
        httpServletResponse: HttpServletResponse
    ): ResponseEntity<BaseResponse>? {
        // token 存在鉴权
        return if (token != null) {
            logService.getAutoLoginLog(token, httpServletRequest, httpServletResponse)
        } else {
            ResultUtil.error(ErrorCode.TOKEN_NOT_FOUNDED, httpServletRequest)
        }
    }
}