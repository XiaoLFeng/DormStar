package com.xlf.dromstarkotlin.exception

import com.frontleaves.general.utils.BaseResponse
import com.frontleaves.general.utils.ErrorCode
import com.frontleaves.general.utils.ResultUtil
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.ResponseEntity
import java.text.SimpleDateFormat
import java.util.*

/**
 * 业务自定义抛出
 *
 * @since v1.0.0
 * @author 筱锋xiao_lfeng
 */
class BusinessException(): RuntimeException() {
    constructor(errorCode: ErrorCode) : this() {
        throw RuntimeException(errorCode.output)
    }

    fun globalReturn(errorMessage: String?, errorCode: ErrorCode, httpServletRequest: HttpServletRequest): ResponseEntity<BaseResponse> {
        return ResultUtil.error(errorCode, errorMessage, httpServletRequest)
    }

    /**
     * 返回前端信息
     */
    fun backInfo(errorCode: ErrorCode, httpServletRequest: HttpServletRequest): ResponseEntity<BaseResponse> {
        return ResultUtil.error(errorCode, httpServletRequest)
    }
}