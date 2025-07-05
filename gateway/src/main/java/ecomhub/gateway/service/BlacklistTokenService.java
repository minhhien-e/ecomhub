package ecomhub.gateway.service;

import ecomhub.gateway.infrastructure.redis.RedisTokenStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class BlacklistTokenService {
    private final RedisTokenStore redisTokenStore;
    private final String BLACKLIST_PREFIX = "blacklist:";

    public Mono<Boolean> isBlacklisted(String jti) {
        return redisTokenStore.hasKey(BLACKLIST_PREFIX, jti);
    }

}
