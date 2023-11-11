package com.xiaolfeng.dormstar.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xiaolfeng.dormstar.entities.DrcomLoginEntity;
import com.xiaolfeng.dormstar.entities.UserEntity;
import com.xiaolfeng.dormstar.mapper.UserMapper;
import com.xiaolfeng.dormstar.utils.SwitchingPeriod;
import lombok.RequiredArgsConstructor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author 筱锋xiao_lfeng
 * @version v1.0.0
 */
@Component
@RequiredArgsConstructor
public class DayScheduleService {
    private final UserMapper userMapper;
    private Request login;
    private Request loginDefault;

    @Scheduled(fixedDelay = 60000)
    public void executePeriodicTask() {
        Date nowDate = new Date();
        UserEntity[] userEntities = userMapper.getAllUser();
        Random random = new Random();
        int getLong = random.nextInt(userEntities.length);
        LocalTime localTime = LocalTime.now(ZoneId.systemDefault());
        if (localTime.isAfter(LocalTime.of(7, 0, 0)) && localTime.isBefore(LocalTime.of(23, 59, 59))) {
            this.login = new Request.Builder()
                    .url("http://10.1.99.100:801/eportal/portal/login?callback=dr1003&login_method=1&user_account=,0," + userEntities[getLong].getUid() + "@" + userEntities[getLong].getType() + "&user_password=" + userEntities[getLong].getPassword() + "&jsVersion=4.1.3&terminal_type=1&lang=zh-cn&v=8101&lang=zh")
                    .build();
            this.loginDefault = new Request.Builder()
                    .url("http://10.1.99.100:801/eportal/portal/login?callback=dr1003&login_method=1&user_account=,0," + userEntities[getLong].getUid() + "&user_password=" + userEntities[getLong].getPassword() + "&jsVersion=4.1.3&terminal_type=1&lang=zh-cn&v=8101&lang=zh")
                    .build();
        }
        Request loginBody = new Request.Builder()
                .url("http://10.1.99.100/drcom/chkstatus?callback=dr1002&jsVersion=4.X&v=1117&lang=zh")
                .build();

        // Start
        if (localTime.isAfter(LocalTime.of(7, 0, 0))) {
            if (localTime.isBefore(LocalTime.of(23, 59, 59))) {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url("https://suggestion.baidu.com/su?wd=201%E4%BA%AC%E6%B5%B7%E5%B8%82%E5%AE%BF%E8%88%8D")
                        .build();

                try (Response response = client.newCall(request).execute()) {
                    // 检查是否可以获取外网内容
                    if (response.body() != null) {
                        System.out.println(new Date().getTime() + " [INFO] already logged in");
                    }
                } catch (IOException ignoreFirst) {
                    // 检查是否可以访问内网
                    try (Response ignored = client.newCall(loginBody).execute()) {
                        // 检测时间
                        if (SwitchingPeriod.checkTime(nowDate.getTime())) {
                            // 处理登录（网络段）
                            try (Response responseLogin = client.newCall(login).execute()) {
                                if (responseLogin.body() != null) {
                                    String getData = responseLogin.body().string();
                                    Matcher matcher = Pattern.compile("dr1003\\(([^)]+)\\)").matcher(getData);
                                    String data = null;
                                    if (matcher.find()) {
                                        data = matcher.group(1);
                                    }
                                    ObjectMapper objectMapper = new ObjectMapper();
                                    DrcomLoginEntity drocm = objectMapper.readValue(data, DrcomLoginEntity.class);
                                    System.out.println(new Date().getTime() + " [INFO] " + drocm.getMessage());
                                }
                            } catch (IOException ignore) {
                                System.out.println(new Date().getTime() + " [FAILED] login failed");
                            }
                        } else {
                            // 处理登录（非网络段23点）
                            try (Response responseLogin = client.newCall(loginDefault).execute()) {
                                if (responseLogin.body() != null) {
                                    String getData = responseLogin.body().string();
                                    Matcher matcher = Pattern.compile("dr1003\\(([^)]+)\\)").matcher(getData);
                                    String data = null;
                                    if (matcher.find()) {
                                        data = matcher.group(1);
                                    }
                                    ObjectMapper objectMapper = new ObjectMapper();
                                    DrcomLoginEntity drocm = objectMapper.readValue(data, DrcomLoginEntity.class);
                                    // 处理登出后登录
                                    if ("Authentication fail".equals(drocm.getMessage())) {
                                        Request removeLogin = new Request.Builder()
                                                .url("http://10.1.99.100:801/eportal/portal/mac/unbind?callback=dr1002&user_account=" + userEntities[getLong].getUid() + "&jsVersion=4.1.3&v=1721&lang=zh")
                                                .build();
                                        Response removeLoginFunction = client.newCall(removeLogin).execute();
                                        removeLoginFunction.close();

                                        // 重新登录
                                        try (Response responseLoginSecond = client.newCall(login).execute()) {
                                            if (responseLoginSecond.body() != null) {
                                                String getDatas = responseLoginSecond.body().string();
                                                Matcher matchers = Pattern.compile("dr1003\\(([^)]+)\\)").matcher(getDatas);
                                                data = null;
                                                if (matchers.find()) {
                                                    data = matchers.group(1);
                                                }
                                                drocm = objectMapper.readValue(data, DrcomLoginEntity.class);
                                                System.out.println(new Date().getTime() + " [INFO] " + drocm.getMessage());
                                            }
                                        } catch (IOException ignore) {
                                            System.out.println(new Date().getTime() + " [FAILED] login failed");
                                        }
                                    }
                                    System.out.println(new Date().getTime() + " [INFO] " + drocm.getMessage());
                                }
                            } catch (IOException ignore) {
                                System.out.println(new Date().getTime() + " [FAILED] login failed");
                            }
                        }
                    } catch (IOException ignoreSecond) {
                        System.out.println(new Date().getTime() + " [WARNING] Not in the campus network, please manually manage the network");
                    }
                }
            }
        }
    }
}
