package com.hanshin.shop.config.redis;


import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

@RequiredArgsConstructor
@EnableCaching
@Configuration
public class RedisCacheConfig extends CachingConfigurerSupport {

    private final ObjectMapper objectMapper;
    private final RedisConnectionFactory connectionFactory; //아까 등록해뒀던 캐시팩토리


    @Bean
    public CacheManager cacheManager(){
        RedisCacheConfiguration redisConfiguration = RedisCacheConfiguration.defaultCacheConfig()
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()))
                .entryTtl(Duration.ofSeconds(30)); //TTL 적용 가능
        return RedisCacheManager.RedisCacheManagerBuilder.fromConnectionFactory(connectionFactory) //Connect 적용
                .cacheDefaults(redisConfiguration).build();
    }
}