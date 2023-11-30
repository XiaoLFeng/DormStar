package com.xlf.dromstarkotlin.mapper

import com.xlf.dromstarkotlin.entity.doData.AccountDO
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Select

@Mapper
interface AccountMapper {

    @Select("SELECT COUNT(*) FROM ds_account")
    fun getCountAccount(): Int

    @Select("SELECT * FROM ds_account WHERE id = #{nextInt}")
    fun getAccountById(nextInt: Int): AccountDO
}