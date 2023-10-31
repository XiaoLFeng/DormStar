package com.xiaolfeng.dormstar.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xiaolfeng.dormstar.entities.DrcomEntity;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.ibatis.jdbc.Null;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author 筱锋xiao_lfeng
 */
@Service
public class GetWxxyNetworkInfo {

    public void getWxxyNetWork(Model model) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("http://10.1.99.100/drcom/chkstatus?callback=dr1002&jsVersion=4.X&v=1117&lang=zh")
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.body() != null) {
                String getData = response.body().string();
                Matcher matcher = Pattern.compile("dr1002\\(([^)]+)\\)").matcher(getData);
                String dr1002 = null;
                if (matcher.find()) {
                    dr1002 = matcher.group(1);
                }
                ObjectMapper objectMapper = new ObjectMapper();
                DrcomEntity drocm = objectMapper.readValue(dr1002, DrcomEntity.class);
                model.addAttribute("getIpv4", drocm.getIpv4());
                model.addAttribute("getLoginIp", drocm.getLoginIp());
                model.addAttribute("getUid", drocm.getUid());
                if (drocm.getUid() != null) {
                    Matcher getStudentNumber = Pattern.compile("^[0-9]+").matcher(drocm.getUid());
                    Matcher getServiceProvider = Pattern.compile("[A-Za-z]+$").matcher(drocm.getUid());
                    if (getStudentNumber.find()) {
                        model.addAttribute("getStudentNumber", getStudentNumber.group(0));
                    }
                    if (getServiceProvider.find()) {
                        if ("cmcc".equals(getServiceProvider.group(0))) {
                            model.addAttribute("getServiceProvider", "移动节点");
                        } else if ("telecom".equals(getServiceProvider.group(0))) {
                            model.addAttribute("getServiceProvider", "电信节点");
                        } else if ("uniom".equals(getServiceProvider.group(0))) {
                            model.addAttribute("getServiceProvider", "联通节点");
                        } else {
                            model.addAttribute("getServiceProvider", "无锡学院");
                        }
                    } else {
                        model.addAttribute("getServiceProvider", "未知");
                    }
                    model.addAttribute("getLogin", "已登录");
                } else  {
                    model.addAttribute("getLogin", "未登录");
                    System.out.println("未登录");
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
