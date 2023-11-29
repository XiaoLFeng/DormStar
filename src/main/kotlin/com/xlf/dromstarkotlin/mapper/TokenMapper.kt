package com.xlf.dromstarkotlin.mapper

import com.xlf.dromstarkotlin.entity.doData.TokenDO
import org.apache.ibatis.annotations.Delete
import org.apache.ibatis.annotations.Insert
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Select
import org.apache.ibatis.annotations.Update

@Mapper
interface TokenMapper {
    @Select("SELECT * FROM dormstar.ds_token")
    fun getAllToken(): Array<TokenDO>?

    @Select("SELECT * FROM dormstar.ds_token WHERE token = #{token}")
    fun getToken(token: String): TokenDO?

    @Delete("DELETE FROM dormstar.ds_token WHERE token = #{token}")
    fun deleteToken(token: String): Boolean

    @Insert("INSERT INTO dormstar.ds_token (user_id, token, user_agent, ip, created_at) VALUES (#{userId}, #{token}, #{userAgent}, #{ip}, #{createdAt})")
    fun insertToken(token: TokenDO): Boolean

    @Update("UPDATE dormstar.ds_token SET user_id = #{userId}, updated_at = #{updatedAt} WHERE token = #{token}")
    fun tokenAuthorization(token: TokenDO): Boolean
}