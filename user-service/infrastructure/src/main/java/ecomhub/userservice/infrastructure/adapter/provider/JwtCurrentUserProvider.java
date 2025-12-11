package ecomhub.userservice.infrastructure.adapter.provider;

import org.springframework.stereotype.Component;
import ecomhub.userservice.application.port.in.provider.CurrentUserProviderPort;

import java.util.UUID;

@Component
public class JwtCurrentUserProvider implements CurrentUserProviderPort {
    public UUID getCurrentUserId() {
        // Mock implementation for now
        return UUID.fromString("3fa85f64-5717-4562-b3fc-2c963f66afa6");
    }
}
