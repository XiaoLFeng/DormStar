package com.xlf.dromstarkotlin

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import java.util.*

@SpringBootTest
class DromStarKotlinApplicationTests {
    @Test
    fun contextLoads() {
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
        println(token)
        assert(true)
    }

}
