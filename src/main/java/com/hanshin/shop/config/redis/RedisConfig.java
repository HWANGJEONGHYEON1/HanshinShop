package com.hanshin.shop.config.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@RequiredArgsConstructor
@EnableRedisRepositories
@Configuration
public class RedisConfig {

    private final RedisProperties redisProperties;

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        // Connect와 관련된 객체 보면 HostName Port등을 지정하고 Lettuce까지 지정
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setHostName(redisProperties.getHost());
        redisStandaloneConfiguration.setPort(redisProperties.getPort());
        //redisStandaloneConfiguration.setPassword(redisProperties.getPassword()); //redis에 비밀번호가 설정 되어 있는 경우 설정
        return new LettuceConnectionFactory(redisStandaloneConfiguration);
    }

    // 실제로 template 역할 key serializer, value serializer 통해 실제 데이터를 변환하는 과정
    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        // Value Serializer를 통해서 실제 데이터를 변환하는 과정이 필요
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory());
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        // 객체가 json 형태로 변환되는 것을 도와줌

        return redisTemplate;
    }
}
