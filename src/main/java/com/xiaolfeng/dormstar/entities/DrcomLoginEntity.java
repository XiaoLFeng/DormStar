package com.xiaolfeng.dormstar.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 登录信息
 *
 * @author 筱锋xiao_lfeng
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class DrcomLoginEntity {
    @JsonProperty("result")
    private int result;
    @JsonProperty("msg")
    private String message;
}
