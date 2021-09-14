package com.hanshin.shop.config.redis;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class CacheManagerCheck implements CommandLineRunner {
    private final CacheManager cacheManager;

    @Override
    public void run(String... args) throws Exception {
        log.info("\n\n " + "====================\n");
        log.info("Using cache Manager " + this.cacheManager.getClass().getName());
        log.info("\n " + "====================\n");
    }
}
