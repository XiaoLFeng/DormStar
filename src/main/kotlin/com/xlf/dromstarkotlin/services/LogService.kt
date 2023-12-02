package com.xlf.dromstarkotlin.services

import com.frontleaves.general.utils.BaseResponse
import com.frontleaves.general.utils.ErrorCode
import com.frontleaves.general.utils.ResultUtil
import com.xlf.dromstarkotlin.mapper.LogMapper
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class LogService(
    val tokenService: TokenService,
    val logMapper: LogMapper
) {

    /**
     * 获取自动登录日志
     */
    fun getAutoLoginLog(token: String, httpServletRequest: HttpServletRequest, httpServletResponse: HttpServletResponse): ResponseEntity<BaseResponse>? {
        // token 鉴权
        return if (tokenService.tokenVerify(token, httpServletResponse)) {
            // 获取自动登录日志
            ResultUtil.success(logMapper.getAutoLoginLog(), httpServletRequest)
        } else {
            ResultUtil.error(ErrorCode.TOKEN_NOT_FOUNDED, httpServletRequest)
        }
    }
}
