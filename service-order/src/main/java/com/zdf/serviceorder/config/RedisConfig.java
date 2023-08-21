package com.zdf.serviceorder.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class RedisConfig
{
    @Value("${spring.redis.host}")
    private String host;
    @Value("${spring.redis.port}")
    private String port;
    @Value("${spring.redis.database}")
    private int database;

    @Bean
    public RedissonClient redissonClient()
    {
        Config config = new Config();
        String protocol = "redis://";
        config.useSingleServer().setAddress(protocol + host + ":" + port).setDatabase(database);

        return Redisson.create(config);
    }
}
