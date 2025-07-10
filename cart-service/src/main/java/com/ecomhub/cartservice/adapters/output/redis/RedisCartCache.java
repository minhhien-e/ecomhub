package com.ecomhub.cartservice.adapters.output.redis;

import com.ecomhub.cartservice.domain.entity.Cart;
import com.ecomhub.cartservice.domain.port.CartCache;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RedisCartCache implements CartCache {

    private final RedisTemplate<String, Cart> redisTemplate;

    @Override
    public Cart get(String userId) {
        return redisTemplate.opsForValue().get(userId);
    }

    @Override
    public void set(String userId, Cart cart) {
        redisTemplate.opsForValue().set(userId, cart);
    }
}