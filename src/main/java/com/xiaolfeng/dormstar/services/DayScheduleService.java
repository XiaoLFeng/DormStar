package com.xiaolfeng.dormstar.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xiaolfeng.dormstar.entities.DrcomEntity;
import com.xiaolfeng.dormstar.entities.DrcomLoginEntity;
import com.xiaolfeng.dormstar.entities.UserEntity;
import com.xiaolfeng.dormstar.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.SimpleDateFormat;
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
    private Request loginBody;

    @Scheduled(cron = "0 0 7 * * ?")
    public void morningAutoLogin() {
        UserEntity[] userEntities = userMapper.getAllUser();
        Random random = new Random();
        int getLong = random.nextInt(userEntities.length);
        this.login = new Request.Builder()
                .url("http://10.1.99.100:801/eportal/portal/login?callback=dr1003&login_method=1&user_account=,0," + userEntities[getLong].getUid() + "@" + userEntities[getLong].getType() + "&user_password=" + userEntities[getLong].getPassword() + "&jsVersion=4.1.3&terminal_type=1&lang=zh-cn&v=8101&lang=zh")
                .build();
        this.loginBody = new Request.Builder()
                .url("http://10.1.99.100/drcom/chkstatus?callback=dr1002&jsVersion=4.X&v=1117&lang=zh")
                .build();

        // Start
        OkHttpClient client = new OkHttpClient();
        // 检查是否可以访问内网
        try (Response ignored = client.newCall(loginBody).execute()) {
            // 处理登录
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
            } catch (IOException ignoredSecond) {
                System.out.println(new SimpleDateFormat().format("yyyy-mm-dd HH:ii:ss") + " [WARNING] be unable to log in");
            }
        } catch (IOException ignoredFirst) {
            System.out.println(new SimpleDateFormat().format("yyyy-mm-dd HH:ii:ss") + " [WARNING] Not in the campus network, please manually manage the network");
        }
    }

    @Scheduled(fixedDelay = 60000)
    public void executePeriodicTask() {
        UserEntity[] userEntities = userMapper.getAllUser();
        Random random = new Random();
        int getLong = random.nextInt(userEntities.length);
        LocalTime localTime = LocalTime.now(ZoneId.systemDefault());
        if (localTime.isAfter(LocalTime.of(7, 0, 0)) && localTime.isBefore(LocalTime.of(23, 59, 59))) {
            this.login = new Request.Builder()
                    .url("http://10.1.99.100:801/eportal/portal/login?callback=dr1003&login_method=1&user_account=,0," + userEntities[getLong].getUid() + "@" + userEntities[getLong].getType() + "&user_password=" + userEntities[getLong].getPassword() + "&jsVersion=4.1.3&terminal_type=1&lang=zh-cn&v=8101&lang=zh")
                    .build();
        }
        this.loginBody = new Request.Builder()
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
                        // 处理登录
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
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    } catch (IOException ignoreSecond) {
                        System.out.println(new Date().getTime() + " [WARNING] Not in the campus network, please manually manage the network");
                    }
                }
            }
        }
    }

    @Scheduled(cron = "0 0 23 * * 0-4")
    public void theWorkDay() {
        this.theDayChangeNetwork();
    }

    @Scheduled(cron = "0 30 23 * * 5-6")
    public void theWeekendDay() {
        this.theDayChangeNetwork();
    }

    public void theDayChangeNetwork() {
        UserEntity[] userEntities = userMapper.getAllUser();
        Random random = new Random();
        int getLong = random.nextInt(userEntities.length);
        this.login = new Request.Builder()
                .url("http://10.1.99.100:801/eportal/portal/login?callback=dr1003&login_method=1&user_account=,0," + userEntities[getLong].getUid() + "&user_password=061823zcw&jsVersion=4.1.3&terminal_type=1&lang=zh-cn&v=6795&lang=zh")
                .build();
        this.loginBody = new Request.Builder()
                .url("http://10.1.99.100/drcom/chkstatus?callback=dr1002&jsVersion=4.X&v=1117&lang=zh")
                .build();

        // Start
        OkHttpClient client = new OkHttpClient();
        // 获取当前登录信息
        try (Response response = client.newCall(loginBody).execute()) {
            // 处理信息
            if (response.body() != null) {
                // 获取数据
                String getData = response.body().string();
                Matcher matcher = Pattern.compile("dr1002\\(([^)]+)\\)").matcher(getData);
                String data = null;
                if (matcher.find()) {
                    data = matcher.group(1);
                }
                DrcomEntity drcom = new ObjectMapper().readValue(data, DrcomEntity.class);
                Matcher getUser = Pattern.compile("^[0-9]+").matcher(drcom.getUid());
                String user = getUser.group(1);
                // 解除当前登录
                Request removeLogin = new Request.Builder()
                        .url("http://10.1.99.100:801/eportal/portal/mac/unbind?callback=dr1002&user_account=" + user + "&jsVersion=4.1.3&v=1721&lang=zh")
                        .build();
                try (Response responseRemoveLogin = client.newCall(removeLogin).execute()) {
                    if (responseRemoveLogin.body() != null) {
                        // 处理无锡学院登录
                        try (Response responseDefault = client.newCall(login).execute()) {
                            if (responseDefault.body() != null) {
                                String getDataDefault = responseDefault.body().string();
                                Matcher matcherDefault = Pattern.compile("dr1003\\(([^)]+)\\)").matcher(getDataDefault);
                                String dataDefault = null;
                                if (matcher.find()) {
                                    dataDefault = matcherDefault.group(1);
                                }
                                ObjectMapper objectMapper = new ObjectMapper();
                                DrcomLoginEntity drocm = objectMapper.readValue(dataDefault, DrcomLoginEntity.class);
                                System.out.println(new Date().getTime() + "[INFO] " + drocm.getMessage());
                            }
                        } catch (IOException ignored) {
                            System.out.println(new SimpleDateFormat().format("yyyy-mm-dd HH:ii:ss") + " [WARNING] Not in the campus network, please manually manage the network");
                        }
                    } else {
                        System.out.println("[ERROR] Object server exception");
                    }
                } catch (IOException ignored) {
                    System.out.println(new SimpleDateFormat().format("yyyy-mm-dd HH:ii:ss") + " [WARNING] Not in the campus network, please manually manage the network");
                }
            } else {
                System.out.println("[ERROR] Object server exception");
            }
        } catch (IOException ignored) {
            System.out.println(new SimpleDateFormat().format("yyyy-mm-dd HH:ii:ss") + " [WARNING] Not in the campus network, please manually manage the network");
        }
    }
}
