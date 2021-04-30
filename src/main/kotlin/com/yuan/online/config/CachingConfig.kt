package com.yuan.online.config

import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.cache.RedisCacheConfiguration
import org.springframework.data.redis.cache.RedisCacheManager
import org.springframework.data.redis.cache.RedisCacheWriter
import org.springframework.data.redis.connection.RedisConnectionFactory
import java.time.Duration

@Configuration
@EnableCaching
class CachingConfig {

    @Bean
    fun redisCacheManager(connectionFactory: RedisConnectionFactory): RedisCacheManager {
        val redisCacheWriter = RedisCacheWriter.lockingRedisCacheWriter(connectionFactory)
        var cacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
        cacheConfiguration = cacheConfiguration.entryTtl(Duration.ofSeconds(30))
        return RedisCacheManager(redisCacheWriter, cacheConfiguration)
    }
}