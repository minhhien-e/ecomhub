package ecomhub.authservice.application.port.repository;

import java.time.Instant;

public interface BlacklistRepositoryPort {
    boolean isBlacklisted(String jti);

    void addToBlacklist(String jti, Instant expiryTime, long ttlInSeconds);

    boolean removeFromBlacklist(String jti);
}
