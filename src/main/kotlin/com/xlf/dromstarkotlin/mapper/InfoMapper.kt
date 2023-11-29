package com.xlf.dromstarkotlin.mapper

import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Select

@Mapper
interface InfoMapper {

    @Select("SELECT * FROM dormstar.ds_info WHERE value = 'register'")
    fun getRegister(): Boolean
}