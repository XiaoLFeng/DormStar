package com.xlf.dromstarkotlin.exception

import com.frontleaves.general.utils.BaseResponse
import com.frontleaves.general.utils.ErrorCode
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class BusinessHttpRequestMethodNotSupportedException {

    @ExceptionHandler(HttpRequestMethodNotSupportedException::class)
    fun handleCustomException(ignore: HttpRequestMethodNotSupportedException, httpServletRequest: HttpServletRequest): ResponseEntity<BaseResponse> {
        return BusinessException().globalReturn(ignore.message, ErrorCode.METHOD_NOT_ALLOWED, httpServletRequest)
    }
}