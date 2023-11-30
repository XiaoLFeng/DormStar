package com.xlf.dromstarkotlin.config

import com.xlf.dromstarkotlin.cache.CacheData
import com.xlf.dromstarkotlin.mapper.InfoMapper
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.stereotype.Component

@Component
class InitialConfiguration(
    val infoMapper: InfoMapper
): ApplicationRunner{

    override fun run(args: ApplicationArguments?) {
        CacheData.autoLogin = infoMapper.autoLogin()
        CacheData.allowRegister = infoMapper.getRegister()
    }
}