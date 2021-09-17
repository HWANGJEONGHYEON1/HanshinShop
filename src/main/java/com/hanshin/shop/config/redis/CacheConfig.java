package com.hanshin.shop.config.redis;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class CacheConfig {
    public final static long EXPIRE_SECONDS = 600;
    public final static String CACHE_PREFIX = "SHOP:";
    public final static String CACHE_LIST_PREFIX = "SHOP:LIST:";
    public final static String CACHE_MANAGER = "cache-manager";
    public final static String CACHE_LIST_MANAGER = "list-cache-manager";
    public final static String KEY_GENERATOR = "key-generator";

    private final RedisConnectionFactory redisConnectionFactory;

    @Bean(CACHE_MANAGER)
    @Primary
    public CacheManager redisCacheManager() {
        log.info("# inside cacheManger....");

        RedisCacheConfiguration redisConfiguration = RedisCacheConfiguration.defaultCacheConfig()
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()))
                .prefixKeysWith(CACHE_PREFIX)
                .entryTtl(Duration.ofSeconds(EXPIRE_SECONDS)); //TTL 적용도 가능하다.

        RedisCacheManager redisCacheManager = RedisCacheManager.RedisCacheManagerBuilder.fromConnectionFactory(redisConnectionFactory) //Connect 적용하고
                .cacheDefaults(redisConfiguration)
                .build(); //캐쉬설정과 관련된 것을 여기에 적용.
        return redisCacheManager;
    }

    @Bean(KEY_GENERATOR)
    public CustomKeyGenerator customKeyGenerator() {
        return new CustomKeyGenerator();
    }

    @Bean(CACHE_LIST_MANAGER)
    public CacheManager redisListCacheManager() {
        log.info("# inside cacheListManger....");

        RedisCacheConfiguration redisConfiguration = RedisCacheConfiguration.defaultCacheConfig()
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()))
                .prefixKeysWith(CACHE_LIST_PREFIX)
                .entryTtl(Duration.ofSeconds(EXPIRE_SECONDS)); //TTL 적용도 가능하다.

        RedisCacheManager redisCacheManager = RedisCacheManager.RedisCacheManagerBuilder.fromConnectionFactory(redisConnectionFactory) //Connect 적용하고
                .cacheDefaults(redisConfiguration)
                .build(); //캐쉬설정과 관련된 것을 여기에 적용.
        return redisCacheManager;
    }

}
