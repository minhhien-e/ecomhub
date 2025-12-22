package ecomhub.authservice.infrastructure.inbound.security.provider;

import ecomhub.authservice.application.port.repository.BlacklistRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2ErrorCodes;
import org.springframework.security.oauth2.core.OAuth2Token;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2ClientAuthenticationToken;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2TokenRevocationAuthenticationToken;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.Instant;

import static ecomhub.authservice.infrastructure.inbound.security.utils.OAuth2ThrowExceptionHelper.throwException;
import static ecomhub.authservice.infrastructure.inbound.security.utils.OAuthorizationHelper.getOAuth2Token;

@Component
@RequiredArgsConstructor
@Transactional
public class PublicClientTokenRevocationAuthenticationProvider implements AuthenticationProvider {
    private final OAuth2AuthorizationService authorizationService;
    private final RegisteredClientRepository registeredClientRepository;
    private final BlacklistRepositoryPort blacklistRepositoryPort;
    public static final String ACCESS_TOKEN = OAuth2TokenType.ACCESS_TOKEN.getValue();
    public static final String REFRESH_TOKEN = OAuth2TokenType.REFRESH_TOKEN.getValue();
    private final JwtDecoder jwtDecoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        OAuth2TokenRevocationAuthenticationToken tokenRevocationAuthentication = (OAuth2TokenRevocationAuthenticationToken) authentication;
        OAuth2ClientAuthenticationToken clientPrincipal = (OAuth2ClientAuthenticationToken) tokenRevocationAuthentication.getPrincipal();
        RegisteredClient registeredClient = registeredClientRepository.findByClientId(clientPrincipal.getPrincipal().toString());
        if (registeredClient == null)
            throwException("Không tìm thấy client", OAuth2ErrorCodes.INVALID_CLIENT);
        String revokeToken = tokenRevocationAuthentication.getToken();
        String tokenTypeHint = tokenRevocationAuthentication.getTokenTypeHint();
        OAuth2Authorization authorization = getAuthorization(revokeToken, tokenTypeHint);
        OAuth2Token authorizationToken = getOAuth2Token(authorization, registeredClient.getId(), revokeToken);
        if (authorizationToken != null) {
            authorization = OAuth2Authorization.from(authorization)
                    .invalidate(authorizationToken)
                    .build();
            authorizationService.save(authorization);
            addToBlacklist(authorizationToken, tokenTypeHint);
            return new OAuth2TokenRevocationAuthenticationToken(authorizationToken, clientPrincipal);
        }
        return tokenRevocationAuthentication;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return OAuth2TokenRevocationAuthenticationToken.class.isAssignableFrom(authentication);
    }

    private OAuth2Authorization getAuthorization(String revokeToken, String tokenTypeHint) {
        OAuth2Authorization authorization = null;
        if (REFRESH_TOKEN.equalsIgnoreCase(tokenTypeHint)) {
            authorization = authorizationService.findByToken(revokeToken, OAuth2TokenType.REFRESH_TOKEN);
        } else if (ACCESS_TOKEN.equalsIgnoreCase(tokenTypeHint)) {
            authorization = authorizationService.findByToken(revokeToken, OAuth2TokenType.ACCESS_TOKEN);
        }
        return authorization;
    }

    private void addToBlacklist(OAuth2Token token, String tokenTypeHint) {
        if (REFRESH_TOKEN.equalsIgnoreCase(tokenTypeHint))
            return;
        Instant expiresAt = token.getExpiresAt();
        Instant issuedAt = token.getIssuedAt();
        if (issuedAt != null) {
            String jti = jwtDecoder.decode(token.getTokenValue()).getClaimAsString("jti");
            blacklistRepositoryPort.addToBlacklist(jti, expiresAt, Duration.between(issuedAt, expiresAt).getSeconds());
        }
    }
}
