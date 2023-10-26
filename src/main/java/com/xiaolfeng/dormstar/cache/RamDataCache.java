package com.xiaolfeng.dormstar.cache;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author lfeng
 */
@Component
public class RamDataCache {
    @Value("${project.version}")
    public String version;
    @Value("${project.name}")
    public String name;
}
