package ecomhub.authservice.infrastructure.inbound.security.provider;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthorizationCodeAuthenticationProvider;
import org.springframework.security.oauth2.client.endpoint.RestClientAuthorizationCodeTokenResponseClient;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DelegatingPublicClientTokenAuthenticationProvider implements AuthenticationProvider {
    private final List<AuthenticationProvider> delegates;

    public DelegatingPublicClientTokenAuthenticationProvider(PublicClientRefreshTokenAuthenticationProvider refreshTokenAuthenticationProvider
    ) {
        this.delegates = new ArrayList<>();
        delegates.add(refreshTokenAuthenticationProvider);
        delegates.add(new OAuth2AuthorizationCodeAuthenticationProvider(new RestClientAuthorizationCodeTokenResponseClient()));
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        for (AuthenticationProvider delegate : delegates) {
            if (delegate.supports(authentication.getClass()))
                return delegate.authenticate(authentication);
        }
        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return delegates.stream()
                .anyMatch(delegate -> delegate.supports(authentication));
    }
}
