package ecomhub.authservice.infrastructure.security.provider;

import ecomhub.authservice.infrastructure.security.validator.PublicClientValidator;
import ecomhub.authservice.infrastructure.security.validator.RefreshTokenValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2Token;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AccessTokenAuthenticationToken;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2ClientAuthenticationToken;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2RefreshTokenAuthenticationToken;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.token.DefaultOAuth2TokenContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenGenerator;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class PublicClientRefreshTokenAuthenticationProvider implements AuthenticationProvider {

    private final OAuth2TokenGenerator<OAuth2Token> tokenGenerator;
    private final PublicClientValidator publicClientValidator;
    private final RefreshTokenValidator refreshTokenValidator;
    private final RegisteredClientRepository registeredClientRepository;
    private final OAuth2AuthorizationService authorizationService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        OAuth2RefreshTokenAuthenticationToken refreshAuth = (OAuth2RefreshTokenAuthenticationToken) authentication;
        OAuth2ClientAuthenticationToken clientAuth = (OAuth2ClientAuthenticationToken) refreshAuth.getPrincipal();
        RegisteredClient registeredClient = registeredClientRepository.findByClientId(clientAuth.getPrincipal().toString());
        publicClientValidator.validate(registeredClient);
        OAuth2Authorization authorization = authorizationService.findByToken(refreshAuth.getRefreshToken(), OAuth2TokenType.REFRESH_TOKEN);
        refreshTokenValidator.validate(authorization, registeredClient);
        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return OAuth2RefreshTokenAuthenticationToken.class.isAssignableFrom(authentication);
    }

}
