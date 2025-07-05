package ecomhub.gateway.infrastructure.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class RedisTokenStore {
    private final ReactiveRedisTemplate<String, String> reactiveRedisTemplate;

    public Mono<Boolean> hasKey(String prefix, String jti) {
        return reactiveRedisTemplate.hasKey(prefix + jti);
    }
}
