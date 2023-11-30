package com.xlf.dromstarkotlin.services

import com.google.gson.Gson
import com.xlf.dromstarkotlin.cache.CacheData
import com.xlf.dromstarkotlin.entity.doData.AccountDO
import com.xlf.dromstarkotlin.mapper.AccountMapper
import okhttp3.OkHttpClient
import okhttp3.Request
import org.springframework.stereotype.Service
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern
import kotlin.collections.HashMap

@Service
class AccountService(
    val accountMapper: AccountMapper
) {
    // 进行 okHttp 配置
    val okHttpClient = OkHttpClient()

    fun checkWhetherYouAreLoggedIn(): Boolean {
        // 获取百度网站
        val request = Request.Builder()
            .url("https://www.baidu.com")
            .build()
        try {
            val response = okHttpClient.newCall(request).execute()
            return if (response.code == 200) {
                println("${SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Date())} [Log] <200>CheckWhetherYouAreLoggedInSuccess")
                true
            } else {
                println("${SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Date())} [Log] <${response.code}>CheckWhetherYouAreLoggedInFailed")
                false
            }
        } catch (e: IOException) {
            println("${SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Date())} [Log] <500>CheckWhetherYouAreLoggedInFailed | ${e.message}")
        }
        return false
    }

    fun regularLogin() {
        // 获取登录地址
        val getAccountDO = randomAccount()
        val getLink = "http://10.1.99.100:801${CacheData.LOGIN_METHOD}&user_account=,0,${getAccountDO.user}@${getAccountDO.type}&user_password=${getAccountDO.password}"
        val request = Request.Builder()
            .url(getLink)
            .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/119.0.0.0 Safari/537.36 Edg/119.0.0.0")
            .build()
        try {
            val response = okHttpClient.newCall(request).execute()
            val matcher = Pattern.compile("dr1003\\(([^)]+)\\)").matcher(response.body!!.string())
            matcher.find()
            val getResponseBody = Gson().fromJson(matcher.group(1), HashMap::class.java)
            if (getResponseBody["result"] == "1") {
                println("${SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Date())} [Log] <200>AutoLoginSuccess | Message:${getResponseBody["msg"]}")
            } else if (getResponseBody["result"] == "0") {
                println("${SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Date())} [Log] <200>AutoLoginAccountDoesNotExist | Message:${getResponseBody["msg"]}")
            } else {
                println("${SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Date())} [Log] <200>AutoLoginUnknownError | Message:${getResponseBody["msg"]}")
            }
        } catch (e: IOException) {
            println("${SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Date())} [Log] <500>AutoLoginFailed | ${e.message}")
        }
    }

    fun regularLogout() {
        // 获取信息
        val requestInfo = Request.Builder()
            .url("http://localhost:8080/api/account/info")
            .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/119.0.0.0 Safari/537.36 Edg/119.0.0.0")
            .build()
        try {
            val response = okHttpClient.newCall(requestInfo).execute()
            val getResponseBody = Gson().fromJson(response.body!!.string(), HashMap::class.java)
                .also { Gson().fromJson(it["data"].toString(), HashMap::class.java) }
            // 获取登出地址
            val getLink = "http://10.1.99.100:801${CacheData.LOGOUT_METHOD}&user_account=${getResponseBody["uid"]}"
            val request = Request.Builder()
                .url(getLink)
                .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko)")
                .build()
            try {
                val response = okHttpClient.newCall(request).execute()
                val matcher = Pattern.compile("dr1003\\(([^)]+)\\)").matcher(response.body!!.string())
                matcher.find()
                val getResponseBody = Gson().fromJson(matcher.group(1), HashMap::class.java)
                if (getResponseBody["result"] == "1") {
                    println("${SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Date())} [Log] <200>AutoLogoutSuccess | Message:${getResponseBody["msg"]}")
                } else if (getResponseBody["result"] == "0") {
                    println("${SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Date())} [Log] <200>AutoLogoutAccountDoesNotExist | Message:${getResponseBody["msg"]}")
                } else {
                    println("${SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Date())} [Log] <200>AutoLogoutUnknownError | Message:$getResponseBody")
                }
            } catch (e: IOException) {
                println("${SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Date())} [Log] <500>AutoLogoutFailed | ${e.message}")
            }
        } catch (e: IOException) {
            println("${SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Date())} [Log] <500>InternalServiceError | ${e.message}")
        }
    }

    fun getInformation(): HashMap<*, *>? {
        // 获取当前信息
        val getLink = "http://" + "10.1.99.100" + CacheData.STATUS_METHOD
        val request = Request.Builder()
            .url(getLink)
            .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko)")
            .build()
        return try {
            val response = okHttpClient.newCall(request).execute()
            val matcher = Pattern.compile("dr1002\\(([^)]+)\\)").matcher(response.body!!.string())
            matcher.find()
            Gson().fromJson(matcher.group(1), HashMap::class.java)
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }

    fun switchTheCampusNetwork() {
        // 获取登录地址
        val getAccountDO = randomAccount()
        val getLink = "http://10.1.99.100:801${CacheData.LOGIN_METHOD}&user_account=,0,${getAccountDO.user}&user_password=${getAccountDO.password}"
        val request = Request.Builder()
            .url(getLink)
            .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/119.0.0.0 Safari/537.36 Edg/119.0.0.0")
            .build()
        try {
            val response = okHttpClient.newCall(request).execute()
            val matcher = Pattern.compile("dr1003\\(([^)]+)\\)").matcher(response.body!!.string())
            matcher.find()
            val getResponseBody = Gson().fromJson(matcher.group(1), HashMap::class.java)
            if (getResponseBody["result"] == "1") {
                println("${SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Date())} [Log] <200>AutoLoginSuccess[School] | Message:${getResponseBody["msg"]}")
            } else if (getResponseBody["result"] == "0") {
                println("${SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Date())} [Log] <200>AutoLoginAccountDoesNotExist[School] | Message:${getResponseBody["msg"]}")
            } else {
                println("${SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Date())} [Log] <200>AutoLoginUnknownError[School] | Message:${getResponseBody["msg"]}")
            }
        } catch (e: IOException) {
            println("${SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Date())} [Log] <500>AutoLoginFailed[School] | ${e.message}")
        }
    }

    fun randomAccount(): AccountDO {
        // 随机获取用户
        val getCount = accountMapper.getCountAccount()
        return accountMapper.getAccountById(Random().nextInt(getCount)+1)
    }
}