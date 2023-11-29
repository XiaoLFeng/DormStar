package com.xlf.dromstarkotlin.services

import com.frontleaves.general.utils.ErrorCode
import com.xlf.dromstarkotlin.cache.CacheData
import com.xlf.dromstarkotlin.cache.CacheToken
import com.xlf.dromstarkotlin.exception.BusinessException
import jakarta.servlet.http.HttpServletRequest
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes
import java.util.Date

@Aspect
@Component
class PreOperation {

    /**
     * 对用户访问量进行控制
     */
    @Around("execution(* com.xlf.dromstarkotlin.controllers.TokenController.*(..))")
    fun tokenCheckService(proceedingJoinPoint: ProceedingJoinPoint): ResponseEntity<out Any>? {
        val requestAttributes = RequestContextHolder.getRequestAttributes() as ServletRequestAttributes?
        val httpServletRequest = requestAttributes?.request as HttpServletRequest
        // 检查
        if (CacheData.tokenVisits[httpServletRequest.remoteAddr] == null) {
            CacheData.tokenVisits[httpServletRequest.remoteAddr] = CacheToken(1, Date().time)
        } else {
            if (CacheData.tokenVisits[httpServletRequest.remoteAddr]!!.timestamp + 60000 > Date().time) {
                if (CacheData.tokenVisits[httpServletRequest.remoteAddr]!!.count > 20L) {
                    return BusinessException().backInfo(ErrorCode.QPS_LIMITATION_VISIT, httpServletRequest)
                } else {
                    CacheData.tokenVisits[httpServletRequest.remoteAddr]!!.count += 1
                }
            } else {
                // 删除内容
                CacheData.tokenVisits.remove(httpServletRequest.remoteAddr)
            }
        }
        return proceedingJoinPoint.proceed() as ResponseEntity<*>?
    }
}