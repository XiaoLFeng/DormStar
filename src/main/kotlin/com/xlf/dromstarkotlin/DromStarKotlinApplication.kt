package com.xlf.dromstarkotlin

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.EnableAspectJAutoProxy
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableAspectJAutoProxy
@EnableScheduling
class DromStarKotlinApplication

fun main(args: Array<String>) {
    runApplication<DromStarKotlinApplication>(*args)
}
