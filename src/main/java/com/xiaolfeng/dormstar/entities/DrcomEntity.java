package com.xiaolfeng.dormstar.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 绑定信息
 *
 * @author 筱锋xiao_lfeng
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class DrcomEntity {
    @JsonProperty("time")
    private int time;
    @JsonProperty("v46ip")
    private String ipv4;
    @JsonProperty("ss6")
    private String loginIp;
    @JsonProperty("uid")
    private String uid;
}
