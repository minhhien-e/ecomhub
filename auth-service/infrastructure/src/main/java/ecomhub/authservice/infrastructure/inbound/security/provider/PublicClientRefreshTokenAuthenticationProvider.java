package ecomhub.authservice.infrastructure.inbound.security.provider;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2ErrorCodes;
import org.springframework.security.oauth2.core.OAuth2Token;
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

import static ecomhub.authservice.infrastructure.inbound.security.utils.OAuth2ThrowExceptionHelper.throwException;
import static ecomhub.authservice.infrastructure.inbound.security.utils.OAuthorizationHelper.getOAuth2Token;

@Component
@RequiredArgsConstructor
@Slf4j
public class PublicClientRefreshTokenAuthenticationProvider implements AuthenticationProvider {
    private final OAuth2TokenGenerator<OAuth2Token> tokenGenerator;
    private final RegisteredClientRepository registeredClientRepository;
    private final OAuth2AuthorizationService authorizationService;
    private final UserDetailsService userDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        OAuth2RefreshTokenAuthenticationToken refreshAuth = (OAuth2RefreshTokenAuthenticationToken) authentication;
        OAuth2ClientAuthenticationToken clientPrincipal = (OAuth2ClientAuthenticationToken) refreshAuth.getPrincipal();
        RegisteredClient registeredClient = registeredClientRepository.findByClientId(clientPrincipal.getPrincipal().toString());
        if (registeredClient == null)
            throwException("Client notfound", OAuth2ErrorCodes.INVALID_CLIENT);
        OAuth2Authorization authorization = authorizationService.findByToken(refreshAuth.getRefreshToken(), OAuth2TokenType.REFRESH_TOKEN);
        getOAuth2Token(authorization, registeredClient.getId(), refreshAuth.getRefreshToken());
        assert authorization != null;
        OAuth2TokenContext tokenContext = createTokenContext(registeredClient, authorization, refreshAuth);
        OAuth2Token generatedAccessToken = tokenGenerator.generate(tokenContext);
        if (generatedAccessToken == null)
            throwException("Can't generate token", OAuth2ErrorCodes.INVALID_REQUEST);
        OAuth2AccessToken accessToken = new OAuth2AccessToken(OAuth2AccessToken.TokenType.BEARER,
                generatedAccessToken.getTokenValue(), generatedAccessToken.getIssuedAt(),
                generatedAccessToken.getExpiresAt(), null);
        return new OAuth2AccessTokenAuthenticationToken(registeredClient, clientPrincipal, accessToken);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return OAuth2RefreshTokenAuthenticationToken.class.isAssignableFrom(authentication);
    }

    private OAuth2TokenContext createTokenContext(RegisteredClient registeredClient, OAuth2Authorization authorization, OAuth2RefreshTokenAuthenticationToken refreshAuth) {
        var user = userDetailsService.loadUserByUsername(authorization.getPrincipalName());
        UsernamePasswordAuthenticationToken userPrincipal = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        return DefaultOAuth2TokenContext.builder()
                .registeredClient(registeredClient)
                .principal(userPrincipal)
                .authorizationServerContext(AuthorizationServerContextHolder.getContext())
                .tokenType(OAuth2TokenType.ACCESS_TOKEN)
                .authorizationGrantType(refreshAuth.getGrantType())
                .authorizationGrant(refreshAuth)
                .authorization(authorization)
                .build();
    }

}
