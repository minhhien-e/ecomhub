package ecomhub.authservice.infrastructure.security.provider;

import ecomhub.authservice.infrastructure.security.validator.RefreshTokenValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.core.*;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AccessTokenAuthenticationToken;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2ClientAuthenticationToken;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2RefreshTokenAuthenticationToken;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.context.AuthorizationServerContextHolder;
import org.springframework.security.oauth2.server.authorization.token.DefaultOAuth2TokenContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenGenerator;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class PublicClientRefreshTokenAuthenticationProvider implements AuthenticationProvider {
    private final OAuth2TokenGenerator<OAuth2Token> tokenGenerator;
    private final RefreshTokenValidator refreshTokenValidator;
    private final RegisteredClientRepository registeredClientRepository;
    private final OAuth2AuthorizationService authorizationService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        OAuth2RefreshTokenAuthenticationToken refreshAuth = (OAuth2RefreshTokenAuthenticationToken) authentication;
        OAuth2ClientAuthenticationToken clientPrincipal = (OAuth2ClientAuthenticationToken) refreshAuth.getPrincipal();
        RegisteredClient registeredClient = registeredClientRepository.findByClientId(clientPrincipal.getPrincipal().toString());
        if (registeredClient == null)
            throwInvalidGrant("Không tìm thấy client", OAuth2ErrorCodes.INVALID_CLIENT);
        OAuth2Authorization authorization = authorizationService.findByToken(refreshAuth.getRefreshToken(), OAuth2TokenType.REFRESH_TOKEN);
        if (authorization == null)
            throwInvalidGrant("Không tìm thấy thông tin xác thực", OAuth2ErrorCodes.INVALID_GRANT);
        refreshTokenValidator.validate(authorization, registeredClient);
        OAuth2TokenContext tokenContext = DefaultOAuth2TokenContext.builder()
                .registeredClient(registeredClient)
                .principal(clientPrincipal)
                .authorizationServerContext(AuthorizationServerContextHolder.getContext())
                .tokenType(OAuth2TokenType.ACCESS_TOKEN)
                .authorizationGrantType(refreshAuth.getGrantType())
                .authorizationGrant(refreshAuth)
                .authorization(authorization)
                .build();
        OAuth2Token generatedAccessToken = tokenGenerator.generate(tokenContext);
        if (generatedAccessToken == null)
            throwInvalidGrant("Tạo token không thành công", OAuth2ErrorCodes.INVALID_REQUEST);
        OAuth2AccessToken accessToken = new OAuth2AccessToken(OAuth2AccessToken.TokenType.BEARER,
                generatedAccessToken.getTokenValue(), generatedAccessToken.getIssuedAt(),
                generatedAccessToken.getExpiresAt(), null);
        return new OAuth2AccessTokenAuthenticationToken(registeredClient, clientPrincipal, accessToken);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return OAuth2RefreshTokenAuthenticationToken.class.isAssignableFrom(authentication);
    }

    private void throwInvalidGrant(String message, String code) throws OAuth2AuthenticationException {
        throw new OAuth2AuthenticationException(
                new OAuth2Error(code, message, null)
        );
    }
}
