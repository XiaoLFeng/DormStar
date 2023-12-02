package com.xlf.dromstarkotlin.controllers

import com.frontleaves.general.utils.BaseResponse
import com.frontleaves.general.utils.ResultUtil
import com.xlf.dromstarkotlin.services.AccountService
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.regex.Pattern

@RestController
@RequestMapping("/api/account")
class AccountController(
    val accountService: AccountService
) {
    @GetMapping("/info")
    fun getInfo(httpServletRequest: HttpServletRequest): ResponseEntity<BaseResponse> {
        val hashMap = HashMap<String, Any?>()
        val getMap = accountService.getInformation()
        val matcherUid = Pattern.compile("^[0-9]+").matcher(getMap?.get("uid").toString())
            .also { it.find() }
        val matcherType = Pattern.compile("[a-z]+$").matcher(getMap?.get("uid").toString())
            .also { it.find() }
        try {
            hashMap["ip"] = getMap?.get("v46ip")
            hashMap["time"] = getMap?.get("time")
            hashMap["uid"] = matcherUid.group(0)
            hashMap["type"] = matcherType.group(0)
        } catch (e: IllegalStateException) {
            hashMap["ip"] = getMap?.get("v46ip")
            hashMap["time"] = null
            hashMap["uid"] = matcherUid.group(0)
            hashMap["type"] = null
        }
        return ResultUtil.success(hashMap, httpServletRequest)
    }
}