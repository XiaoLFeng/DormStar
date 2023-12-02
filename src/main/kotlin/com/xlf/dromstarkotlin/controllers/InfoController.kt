package com.xlf.dromstarkotlin.controllers

import com.frontleaves.general.utils.BaseResponse
import com.xlf.dromstarkotlin.services.InfoService
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/info")
class InfoController(
    val infoService: InfoService
) {

    /**
     * 获取内容
     */
    @GetMapping("/data")
    fun getWebInfo(httpServletRequest: HttpServletRequest): ResponseEntity<BaseResponse> {
        return infoService.getWebInfo(httpServletRequest)
    }
}