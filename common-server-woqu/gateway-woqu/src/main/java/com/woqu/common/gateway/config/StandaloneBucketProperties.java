package com.woqu.common.gateway.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

/**
 * @author orrin on 2018/7/3
 */
@ConfigurationProperties(StandaloneBucketProperties.PREFIX)
public class StandaloneBucketProperties {
    public static final String PREFIX = "spring.extend.standalone-bucket";

    private int capacity = -1;
    private int refillTokens = -1;
    private int refillPeriod = 1;

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getRefillTokens() {
        return refillTokens;
    }

    public void setRefillTokens(int refillTokens) {
        this.refillTokens = refillTokens;
    }

    public int getRefillPeriod() {
        return refillPeriod;
    }

    public void setRefillPeriod(int refillPeriod) {
        this.refillPeriod = refillPeriod;
    }

    public boolean shouldWork() {
        return this.capacity > 0 && this.refillTokens > 0;
    }
}
