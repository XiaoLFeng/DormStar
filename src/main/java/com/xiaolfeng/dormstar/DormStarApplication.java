package com.xiaolfeng.dormstar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author 筱锋xiao_lfeng
 * @version v1.0.0
 */
@SpringBootApplication
@EnableScheduling
public class DormStarApplication {
    public static void main(String[] args) {
        SpringApplication.run(DormStarApplication.class, args);
    }

}
