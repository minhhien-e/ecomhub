package ecomhub.productservice.helper;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;
import java.util.UUID;

public final class AuthHelper {

    private AuthHelper() {}

    public static Optional<UUID> getCurrentUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) return Optional.empty();
        Object principal = auth.getPrincipal();
        if (principal == null) return Optional.empty();
        try {
            return Optional.of(UUID.fromString(principal.toString()));
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}