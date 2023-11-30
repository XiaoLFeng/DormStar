package com.xlf.dromstarkotlin.services

import com.xlf.dromstarkotlin.cache.CacheData
import com.xlf.dromstarkotlin.mapper.TokenMapper
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.text.SimpleDateFormat
import java.util.*

@Component
class ScheduleService(
    val tokenMapper: TokenMapper,
    val accountService: AccountService
) {

    /**
     * 清除过期的 Token
     */
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

    /**
     * 登录校园网（5分钟自动检查）
     */
    @Scheduled(fixedDelay = 300000)
    fun schoolLogin() {
        if (CacheData.autoLogin) {
            val calendar = Calendar.getInstance()
            val hour = SimpleDateFormat("HH").format(Date()).toInt()
            val minute = SimpleDateFormat("mm").format(Date()).toInt()
            // 检查星期
            if (calendar.get(Calendar.DAY_OF_WEEK) in 1..5) {
                // 允许登录时间范围
                if (hour in 7..<23) {
                    // 登录
                    if (!accountService.checkWhetherYouAreLoggedIn()) {
                        accountService.regularLogin()
                    }
                } else {
                    // 切换校园网
                    do {
                        accountService.regularLogout()
                        Thread.sleep(5000)
                    } while (accountService.getInformation()?.get("uid") == null)
                    accountService.switchTheCampusNetwork()
                }
            } else {
                if (hour in 7 .. 23) {
                    if (hour < 23 || (hour == 23 && minute in 0..30)) {
                        // 登录
                        if (!accountService.checkWhetherYouAreLoggedIn()) {
                            accountService.regularLogin()
                        }
                    } else{
                        // 切换校园网
                        do {
                            accountService.regularLogout()
                            Thread.sleep(5000)
                        } while (accountService.getInformation()?.get("uid") == null)
                        accountService.switchTheCampusNetwork()
                    }
                }
            }
        }
    }
}