package com.hanshin.shop.redis;

import lombok.Getter;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedisProperties {
    private int redisPort;
    private String redisHost;

    public RedisProperties() {
        this.redisPort = 6370;
        this.redisHost = "localhost";
    }

    public int getRedisPort() {
        return redisPort;
    }

    public String getRedisHost() {
        return redisHost;
    }
}