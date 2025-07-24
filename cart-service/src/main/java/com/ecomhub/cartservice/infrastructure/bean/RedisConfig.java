package com.ecomhub.cartservice.infrastructure.bean;

import com.ecomhub.cartservice.domain.entity.Cart;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    @Bean
    public org.springframework.data.redis.core.RedisTemplate<String, Cart> redisTemplate(RedisConnectionFactory factory) {
        org.springframework.data.redis.core.RedisTemplate<String, Cart> template = new org.springframework.data.redis.core.RedisTemplate<>();
        template.setConnectionFactory(factory);
        template.setKeySerializer(new StringRedisSerializer());
        
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());

        return template;
    }


}
