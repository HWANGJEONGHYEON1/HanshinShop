package com.hanshin.shop.config.redis;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
@ConfigurationProperties(prefix = "spring.redis.cluster")
public class RedisClusterProperties {
    private List<String> nodes;
    private String password;
    private int timeout;
    private int shutdownTimeout;
    private int maxRedirects;
}

