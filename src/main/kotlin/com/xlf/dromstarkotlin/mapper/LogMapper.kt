package com.xlf.dromstarkotlin.mapper

import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Select

@Mapper
interface LogMapper {

    @Select("SELECT * FROM ds_login_log ORDER BY id DESC LIMIT 50")
    fun getAutoLoginLog(): String
}
