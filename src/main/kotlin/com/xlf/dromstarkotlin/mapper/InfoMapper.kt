package com.xlf.dromstarkotlin.mapper

import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Select

@Mapper
interface InfoMapper {

    @Select("SELECT * FROM ds_info WHERE value = 'register'")
    fun getRegister(): Boolean

    @Select("SELECT * FROM ds_info WHERE value = 'schoolLoginAddress'")
    fun getSchoolLoginIpAddress(): String

    @Select("SELECT * FROM ds_info WHERE value = 'autoLogin'")
    fun autoLogin(): Boolean
}