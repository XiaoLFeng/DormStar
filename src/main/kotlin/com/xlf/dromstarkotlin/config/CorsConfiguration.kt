package com.xlf.dromstarkotlin.config

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry

import org.springframework.web.servlet.config.annotation.WebMvcConfigurer


@Configuration
class CorsConfiguration : WebMvcConfigurer {
    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/**") //是否发送Cookie
            .allowCredentials(true) //放行哪些原始域
            .allowedOriginPatterns("*")
            .allowedMethods(*arrayOf("GET", "POST", "PUT", "DELETE"))
            .allowedHeaders("*")
            .exposedHeaders("*")
    }
}