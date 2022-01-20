package com.hanshin.shop.config.redis;

import io.lettuce.core.internal.HostAndPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.ArrayList;
import java.util.List;

@Configuration
@RequiredArgsConstructor
@EnableRedisRepositories
@Slf4j
public class RedisClusterConfig {

    private final RedisClusterProperties redisClusterProperties;

    @Primary
    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        List<RedisNode> nodes = new ArrayList<>();
        for (String client : redisClusterProperties.getNodes()) {
            HostAndPort hostAndPort = HostAndPort.parse(client);
            nodes.add(new RedisNode(hostAndPort.getHostText(), hostAndPort.getPort()));
        }

        RedisClusterConfiguration redisClusterConfiguration = new RedisClusterConfiguration();
        redisClusterConfiguration.setClusterNodes(nodes);
        redisClusterConfiguration.setMaxRedirects(redisClusterProperties.getMaxRedirects());
        redisClusterConfiguration.setPassword(redisClusterProperties.getPassword());

        LettuceConnectionFactory lettuceConnectionFactory = new LettuceConnectionFactory(redisClusterConfiguration);
        lettuceConnectionFactory.afterPropertiesSet();

        return lettuceConnectionFactory;
    }

    @Primary
    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory());
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        return redisTemplate;
    }
}
