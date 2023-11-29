package com.xlf.dromstarkotlin.services

import com.xlf.dromstarkotlin.mapper.TokenMapper
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.text.SimpleDateFormat
import java.util.*

@Component
class ScheduleService(
    val tokenMapper: TokenMapper
) {

    @Scheduled(fixedDelay = 900000)
    fun clearExpiredToken() {
        // 循环所有 Token 信息
        val getToken = tokenMapper.getAllToken()
        var i = 0
        if (getToken != null) {
            for (tokenInfo in getToken) {
                if (tokenInfo.userId != null) {
                    if (tokenInfo.updatedAt != null) {
                        if (tokenInfo.updatedAt!!.time + 43200000 < Date().time) {
                            tokenMapper.deleteToken(tokenInfo.token!!)
                            i++
                        }
                    } else {
                        if (tokenInfo.createdAt!!.time + 43200000 < Date().time) {
                            tokenMapper.deleteToken(tokenInfo.token!!)
                            i++
                        }
                    }
                } else {
                    if (tokenInfo.createdAt!!.time + 300000 < Date().time) {
                        tokenMapper.deleteToken(tokenInfo.token!!)
                        i++
                    }
                }
            }
        }
        println("${SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Date())} [Log] <----->Scheduled | Message:clear token $i")
    }
}