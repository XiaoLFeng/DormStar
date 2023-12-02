package com.xlf.dromstarkotlin.services

import com.frontleaves.general.utils.BaseResponse
import com.frontleaves.general.utils.ResultUtil
import com.xlf.dromstarkotlin.mapper.InfoMapper
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class InfoService(
    val infoMapper: InfoMapper
) {

    /**
     * 获取内容
     */
    fun getWebInfo(httpServletRequest: HttpServletRequest): ResponseEntity<BaseResponse> {
        val hashMap = HashMap<String, Any?>()
        hashMap["title"] = infoMapper.getTitle().data
        hashMap["sub_title"] = infoMapper.getSubTitle().data
        return ResultUtil.success(hashMap, httpServletRequest)
    }

}
