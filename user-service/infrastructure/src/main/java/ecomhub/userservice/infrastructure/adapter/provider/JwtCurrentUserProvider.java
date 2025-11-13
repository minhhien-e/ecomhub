package ecomhub.userservice.infrastructure.adapter.provider;

import ecomhub.userservice.application.port.in.provider.CurrentUserProviderPort;
import org.springframework.stereotype.Component;

import java.util.UUID;
@Component
public class JwtCurrentUserProvider implements CurrentUserProviderPort {
    @Override
    public UUID getCurrentUserId() {
        return null;
    }
}
