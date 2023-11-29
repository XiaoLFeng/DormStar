package com.frontleaves.general.utils

import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import java.text.SimpleDateFormat
import java.util.*

class ResultUtil constructor(val output: String, val code: Int, val message: String, val data: Any?) {
    companion object {
        /**
         * ## 无参正确返回
         *
         * 返回状态码为 200，返回信息为 OK，返回数据为 null
         *
         * @return BaseResponse
         */
        fun success(httpServletRequest: HttpServletRequest): ResponseEntity<BaseResponse> {
            println("${SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Date())} [Log] <200>Success | Message:OK | URI:${httpServletRequest.requestURI}")
            return ResponseEntity
                .status(200)
                .body(BaseResponse())
        }

        /**
         * ## 正确返回
         *
         * 返回状态码为 200，返回信息为 [message]，返回数据为 null
         *
         * @return BaseResponse
         */
        fun success(message: String, httpServletRequest: HttpServletRequest): ResponseEntity<BaseResponse> {
            println("${SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Date())} [Log] <200>Success | Message:$message | URI:${httpServletRequest.requestURI}")
            return ResponseEntity
                .status(200)
                .body(BaseResponse(message, null))
        }

        fun success(output: String, message: String, data: Any?, httpServletRequest: HttpServletRequest): ResponseEntity<BaseResponse> {
            println("${SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Date())} [Log] <200>$output | Message:$message | URI:${httpServletRequest.requestURI}")
            return ResponseEntity
                .status(200)
                .body(BaseResponse(output, 200, message, data))
        }

        /**
         * ## 正确返回
         *
         * 返回状态码为 200，返回信息为 [message]，返回数据为 [data]
         *
         * @return BaseResponse
         */
        fun success(message: String, data: Any?, httpServletRequest: HttpServletRequest): ResponseEntity<BaseResponse> {
            println("${SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Date())} [Log] <200>Success | Message:$message | URI:${httpServletRequest.requestURI}")
            return ResponseEntity
                .status(200)
                .body(BaseResponse(message, data))
        }

        /**
         * ## 正确返回
         *
         * 返回状态码为 200，返回信息为 OK，返回数据为 [data]
         *
         * @return BaseResponse
         */
        fun success(data: Any?, httpServletRequest: HttpServletRequest): ResponseEntity<BaseResponse> {
            return success("OK", data, httpServletRequest)
        }

        /**
         * ## 错误返回
         *
         * 返回数据 [errorCode]
         *
         * 返回内容依据 [errorCode] 内枚举为准，返回数据为 null
         *
         * @return BaseResponse
         */
        fun error(errorCode: ErrorCode, httpServletRequest: HttpServletRequest): ResponseEntity<BaseResponse> {
            println("${SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Date())} [Log] <${errorCode.code}>${errorCode.output} | Message:${errorCode.message} | URI:${httpServletRequest.requestURI}")
            return ResponseEntity
                .status(errorCode.httpStatus)
                .body(BaseResponse(errorCode.output, errorCode.code, errorCode.message, null))
        }

        /**
         * ## 错误返回
         *
         * 返回数据 [errorCode]
         *
         * 返回内容依据 [errorCode] 内枚举为准，描述信息为 [message] ，返回数据为 null
         *
         * @return BaseResponse
         */
        fun error(errorCode: ErrorCode, message: String, httpServletRequest: HttpServletRequest): ResponseEntity<BaseResponse> {
            println("${SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Date())} [Log] <${errorCode.code}>${errorCode.output} | Message:$message | URI:${httpServletRequest.requestURI}")
            return ResponseEntity
                .status(errorCode.httpStatus)
                .body(BaseResponse(errorCode.output, errorCode.code, message, null))
        }

        /**
         * ## 错误返回
         *
         * 返回数据 [errorCode]
         *
         * 返回内容依据 [errorCode] 内枚举为准，描述信息为 [message] ，返回数据为 [data]
         *
         * @return BaseResponse
         */
        fun error(errorCode: ErrorCode, data: Any?, httpServletRequest: HttpServletRequest): ResponseEntity<BaseResponse> {
            println("${SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Date())} [Log] <${errorCode.code}>${errorCode.output} | Message:${errorCode.message} | URI:${httpServletRequest.requestURI}")
            return ResponseEntity
                .status(errorCode.httpStatus)
                .body(BaseResponse(errorCode.output, errorCode.code, errorCode.message, data))
        }

        fun redirect(link:String, httpServletRequest: HttpServletRequest): ResponseEntity<BaseResponse> {
            println("${SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Date())} [Log] <302>Redirect | Message:Redirect to $link | URI:${httpServletRequest.requestURI}")
            return ResponseEntity
                .status(HttpStatus.FOUND)
                .header("Location", link)
                .body(BaseResponse("Redirect", 302, "重定向", null))
        }

        fun redirect(output: String, message: String, data: Any?, link: String?, httpServletRequest: HttpServletRequest): ResponseEntity<BaseResponse> {
            println("${SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Date())} [Log] <200>$output | Message:$message, Redirect to $link | URI:${httpServletRequest.requestURI}")
            return if (link != null) {
                ResponseEntity
                    .status(HttpStatus.FOUND)
                    .header("Location", link)
                    .body(BaseResponse(output, 302, message, data))
            } else {
                ResponseEntity
                    .status(HttpStatus.OK)
                    .body(BaseResponse(output, 200, message, data))
            }
        }
    }
}