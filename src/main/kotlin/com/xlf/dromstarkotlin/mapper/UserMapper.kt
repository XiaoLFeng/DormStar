package com.xlf.dromstarkotlin.mapper

import com.xlf.dromstarkotlin.entity.doData.UserDO
import org.apache.ibatis.annotations.Insert
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Select

@Mapper
interface UserMapper {

    @Select("SELECT * FROM ds_user WHERE user = #{username}")
    fun getUserByUsername(username: String?): UserDO?

    @Select("SELECT * FROM ds_user WHERE user = #{username} OR email = #{email} OR tel = #{phone} LIMIT 1")
    fun getUser(username: String, email: String, phone: String?): UserDO?

    @Insert("INSERT INTO ds_user (user, password, email, tel, created_at) VALUES (#{user}, #{password}, #{email}, #{tel}, #{createdAt})")
    fun insertUser(user: UserDO): Boolean
}