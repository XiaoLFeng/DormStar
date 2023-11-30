package com.xlf.dromstarkotlin.config

import com.google.gson.Gson
import com.xlf.dromstarkotlin.entity.voData.ConfigVO
import com.xlf.dromstarkotlin.entity.voData.MysqlVO
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.apache.ibatis.session.SqlSessionFactory
import org.mybatis.spring.SqlSessionFactoryBean
import org.mybatis.spring.SqlSessionTemplate
import org.mybatis.spring.annotation.MapperScan
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.io.File
import javax.sql.DataSource


@Configuration
@MapperScan("com.xlf.dromstarkotlin.mapper")
class MysqlConfiguration {
    @Bean
    @Throws(Exception::class)
    fun sqlSessionFactory(dataSource: DataSource?, applicationContext: ApplicationContext?): SqlSessionFactory? {
        val factoryBean = SqlSessionFactoryBean()
        factoryBean.setDataSource(dataSource)
        // 配置其他属性，如mapper位置等
        return factoryBean.getObject()
    }

    @Bean
    @Throws(Exception::class)
    fun sqlSessionTemplate(sqlSessionFactory: SqlSessionFactory?): SqlSessionTemplate {
        return SqlSessionTemplate(sqlSessionFactory)
    }


    @Bean
    fun dataSource(): DataSource {
        // 获取json配置文件中的数据库配置
        val gson = Gson()
        // 读取配置文件 config.json
        val getConfig: String = if (File("./config.json").exists()) {
            File("./config.json").readText(Charsets.UTF_8)
        } else {
            File("./config.json").createNewFile()
            // 输入信息进入文件
            File("./config.json").writeText(gson.toJson(ConfigVO(MysqlVO("127.0.0.1", 3306, "root", "123456", "dromstar"))))
            File("./config.json").readText(Charsets.UTF_8)
        }
        val configVO = gson.fromJson(getConfig, ConfigVO::class.java).mysql

        // 数据库链接
        val hikariConfig = HikariConfig()
        hikariConfig.driverClassName = "com.mysql.cj.jdbc.Driver"
        hikariConfig.jdbcUrl = "jdbc:mysql://${configVO.host}:${configVO.port}/${configVO.database}"
        hikariConfig.username = configVO.username
        hikariConfig.password = configVO.password
        return HikariDataSource(hikariConfig)
    }
}