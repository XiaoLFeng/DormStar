package com.xlf.dromstarkotlin.services

import com.frontleaves.general.utils.BaseResponse
import com.frontleaves.general.utils.ErrorCode
import com.frontleaves.general.utils.ResultUtil
import com.xlf.dromstarkotlin.entity.doData.UserDO
import com.xlf.dromstarkotlin.entity.voData.SignInVO
import com.xlf.dromstarkotlin.entity.voData.SignUpVO
import com.xlf.dromstarkotlin.mapper.TokenMapper
import com.xlf.dromstarkotlin.mapper.UserMapper
import jakarta.servlet.http.HttpServletRequest
import org.mindrot.jbcrypt.BCrypt
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.sql.Timestamp
import java.util.*

@Service
class UserService(
    val userMapper: UserMapper,
    val tokenMapper: TokenMapper
) {

    @Transactional
    fun signIn(
        signInVO: SignInVO, token:String, httpServletRequest: HttpServletRequest
    ): ResponseEntity<BaseResponse> {
        // 检查用户是否存在
        val user = userMapper.getUserByUsername(signInVO.username)
        return if (user != null) {
            // 检查用户密码是否匹配
            if (BCrypt.checkpw(signInVO.password, user.password)) {
                val tokenDO = tokenMapper.getToken(token)
                    .also { it!!.userId = user.id }
                if (tokenDO!!.userId == null) {
                    // 授权 token
                    if (tokenMapper.tokenAuthorization(tokenDO)) {
                        ResultUtil.success("登陆成功", httpServletRequest)
                    } else {
                        ResultUtil.error(ErrorCode.DATABASE_UPDATE_OPERATION_FAILED, httpServletRequest)
                    }
                } else {
                    ResultUtil.error(ErrorCode.YOU_ARE_ALREADY_LOGIN, httpServletRequest)
                }
            } else {
                ResultUtil.error(ErrorCode.WRONG_PASSWORD, httpServletRequest)
            }
        } else {
            ResultUtil.error(ErrorCode.USER_NOT_FOUNDED, httpServletRequest)
        }
    }

    @Transactional
    fun signUp(
        signUpVO: SignUpVO, token: String, httpServletRequest: HttpServletRequest
    ): ResponseEntity<BaseResponse> {
        // 检查用户和邮箱是否存在
        val user = userMapper.getUser(signUpVO.username, signUpVO.email, signUpVO.telephone)
        if (user == null) {
            val tokenDO = tokenMapper.getToken(token)
            if (tokenDO!!.userId == null) {
                // 处理用户注册程序
                val hashPassword = BCrypt.hashpw(signUpVO.password, BCrypt.gensalt())
                val newUser = UserDO(null, signUpVO.username, hashPassword, signUpVO.email, signUpVO.telephone, 0, Timestamp(Date().time), null)
                // 数据输入数据库
                return if (userMapper.insertUser(newUser)) {
                    // 授权 token
                    if (tokenMapper.tokenAuthorization(tokenDO)) {
                        ResultUtil.success("注册成功", httpServletRequest)
                    } else {
                        ResultUtil.error(ErrorCode.DATABASE_UPDATE_OPERATION_FAILED, httpServletRequest)
                    }
                } else {
                    ResultUtil.error(ErrorCode.DATABASE_INSERT_OPERATION_FAILED, httpServletRequest)
                }
            } else {
                return ResultUtil.error(ErrorCode.YOU_ARE_ALREADY_LOGIN, httpServletRequest)
            }
        } else {
            return ResultUtil.error(ErrorCode.USER_EXIST, httpServletRequest)
        }
    }
}