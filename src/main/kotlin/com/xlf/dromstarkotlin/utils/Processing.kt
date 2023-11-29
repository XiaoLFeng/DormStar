package com.xlf.dromstarkotlin.utils

import java.util.Date
import java.util.Random

object Processing {

    /**
     * 创建固定长度随机字符串
     */
    fun createRandomString(length: Int) = createRandomString(length, length)

    /**
     * 创建范围长度随机字符串
     */
    fun createRandomString(min: Int, max: Int): String {
        return ""
    }

    /**
     * 创建 Token 令牌
     */
    fun createToken(): String {
        var token = (Date().time/1000).toString()
        val random = Random()
        // 判定随机范围
        var i: Int
        var j = 0
        while (j++ < 3) {
            val x: Int = if(j == 1 || j == 2) 6 else 10
            token += "-"
            i = 0
            while (i < x) {
                if (random.nextBoolean()) {
                    token += random.nextInt(10)
                } else {
                    token += random.nextInt(97, 123).toChar()
                }
                i++
            }
        }
        return token
    }
}