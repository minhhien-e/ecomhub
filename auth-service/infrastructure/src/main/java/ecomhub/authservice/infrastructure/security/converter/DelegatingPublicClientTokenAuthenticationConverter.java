package ecomhub.authservice.infrastructure.security.converter;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.server.authorization.web.authentication.OAuth2AuthorizationCodeAuthenticationConverter;
import org.springframework.security.web.authentication.AuthenticationConverter;

import java.util.ArrayList;
import java.util.List;

public class DelegatingPublicClientTokenAuthenticationConverter implements AuthenticationConverter {
    private final List<AuthenticationConverter> delegates;

    public DelegatingPublicClientTokenAuthenticationConverter() {
        delegates = new ArrayList<>();
        delegates.add(new PublicClientRefreshTokenAuthenticationConverter());
        delegates.add(new OAuth2AuthorizationCodeAuthenticationConverter());
    }

    @Override
    public Authentication convert(HttpServletRequest request) {
        for (AuthenticationConverter converter : delegates) {
            Authentication auth = converter.convert(request);
            if (auth != null) {
                return auth;
            }
        }
        return null;
    }
}
