package ecomhub.authservice.infrastructure.outbound.persistence.repository.blacklist;

import ecomhub.authservice.application.port.repository.BlacklistRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.concurrent.TimeUnit;

@Repository
@RequiredArgsConstructor
public class BlacklistRepository implements BlacklistRepositoryPort {
    private final RedisTemplate<String, String> redisTemplate;

    @Override
    public boolean isBlacklisted(String jti) {
        String key = getBlacklistKey(jti);
        return redisTemplate.hasKey(key);
    }

    @Override
    public void addToBlacklist(String jti, Instant expiryTime, long ttlInSeconds) {
        String key = getBlacklistKey(jti);
        redisTemplate.opsForValue().set(key, expiryTime.toString(), ttlInSeconds, TimeUnit.SECONDS);
    }

    @Override
    public boolean removeFromBlacklist(String jti) {
        String key = getBlacklistKey(jti);
        return redisTemplate.delete(key);
    }

    private String getBlacklistKey(String jti) {
        String blacklistKeyFormat = "blacklist:token:%s";
        return String.format(blacklistKeyFormat, jti);
    }
}
