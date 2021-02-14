package com.laoji.redis_demo.config;

import org.redisson.Redisson;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedisConfig {
    @Value("${spring.redis.host}")
    public String redisHost;

    @Value("${spring.redis.port}")
    public String redisPort;

    @Bean
    public Redisson redisson() {
        Config config = new Config();
        config.useSingleServer()
                // use "rediss://" for SSL connection
                .setAddress("redis://" + redisHost + ":" + redisPort);
        Redisson redisson = (Redisson) Redisson.create(config);
        return redisson;
    }
}
