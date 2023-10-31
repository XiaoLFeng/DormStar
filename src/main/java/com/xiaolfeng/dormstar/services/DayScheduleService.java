package com.xiaolfeng.dormstar.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xiaolfeng.dormstar.entities.DrcomEntity;
import com.xiaolfeng.dormstar.entities.DrcomLoginEntity;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author 筱锋xiao_lfeng
 * @version v1.0.0
 */
@Component
public class DayScheduleService {
    private final Request login;
    private final Request loginBody;

    public DayScheduleService() {
        login = new Request.Builder()
                .url("http://10.1.99.100:801/eportal/portal/login?callback=dr1003&login_method=1&user_account=,0,22344233@cmcc&user_password=061823zcw&jsVersion=4.1.3&terminal_type=1&lang=zh-cn&v=8101&lang=zh")
                .build();
        loginBody = new Request.Builder()
                .url("http://10.1.99.100/drcom/chkstatus?callback=dr1002&jsVersion=4.X&v=1117&lang=zh")
                .build();
    }

    @Scheduled(cron = "0 0 7 * * ?")
    public void morningAutoLogin() {
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
                    System.out.println("[INFO] " + drocm.getMessage());
                }
            } catch (IOException ignoredSecond) {
                System.out.println("[WARNING] 无法登录");
            }
        } catch (IOException ignoredFirst) {
            System.out.println("[WARNING] 不处于校园网内，请手动管理网络");
        }
    }

    @Scheduled(fixedDelay = 60000)
    public void executePeriodicTask() {
        LocalTime localTime = LocalTime.now();
        if (localTime.isAfter(LocalTime.of(7, 0)) && localTime.isBefore(LocalTime.of(23, 0))) {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url("https://suggestion.baidu.com/su?wd=201%E4%BA%AC%E6%B5%B7%E5%B8%82%E5%AE%BF%E8%88%8D")
                    .build();

            try (Response response = client.newCall(request).execute()) {
                // 检查是否可以获取外网内容
                if (response.body() != null) {
                    System.out.println("[INFO] 已登录");
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
                            System.out.println("[INFO] " + drocm.getMessage());
                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                } catch (IOException ignoreSecond) {
                    System.out.println("[WARNING] 不处于校园网内，请手动管理网络");
                }
            }
        }
    }

    @Scheduled(cron = "0 0 23 * * 0-4")
    @Scheduled(cron = "0 30 23 * * 5-6")
    public void theDayChangeNetwork() {
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
                        Request loginDefault = new Request.Builder()
                                .url("http://10.1.99.100:801/eportal/portal/login?callback=dr1003&login_method=1&user_account=,0,22344233&user_password=061823zcw&jsVersion=4.1.3&terminal_type=1&lang=zh-cn&v=6795&lang=zh")
                                .build();
                        try (Response responseDefault = client.newCall(loginDefault).execute()) {
                            if (responseDefault.body() != null) {
                                String getDataDefault = responseDefault.body().string();
                                Matcher matcherDefault = Pattern.compile("dr1003\\(([^)]+)\\)").matcher(getDataDefault);
                                String dataDefault = null;
                                if (matcher.find()) {
                                    dataDefault = matcherDefault.group(1);
                                }
                                ObjectMapper objectMapper = new ObjectMapper();
                                DrcomLoginEntity drocm = objectMapper.readValue(dataDefault, DrcomLoginEntity.class);
                                System.out.println("[INFO] " + drocm.getMessage());
                            }
                        } catch (IOException ignored) {
                            System.out.println("[WARNING] 不处于校园网内，请手动管理网络");
                        }
                    } else {
                        System.out.println("[ERROR] 对象服务器异常");
                    }
                } catch (IOException ignored) {
                    System.out.println("[WARNING] 不处于校园网内，请手动管理网络");
                }
            } else {
                System.out.println("[ERROR] 对象服务器异常");
            }
        } catch (IOException ignored) {
            System.out.println("[WARNING] 不处于校园网内，请手动管理网络");
        }
    }
}
