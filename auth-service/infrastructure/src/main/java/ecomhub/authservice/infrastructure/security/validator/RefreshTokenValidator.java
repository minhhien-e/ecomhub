package ecomhub.authservice.infrastructure.security.validator;

import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.OAuth2ErrorCodes;
import org.springframework.security.oauth2.core.OAuth2RefreshToken;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.stereotype.Component;

@Component
public class RefreshTokenValidator {

    public void validate(OAuth2Authorization authorization, RegisteredClient registeredClient) {
        if (authorization == null)
            throwInvalidGrant("Refresh token không tồn tại");
        if (!registeredClient.getClientId().equals(authorization.getRegisteredClientId()))
            throwInvalidGrant("Client Id không khớp");
        OAuth2Authorization.Token<OAuth2RefreshToken> auth2RefreshToken = authorization.getRefreshToken();
        if (auth2RefreshToken == null)
            throwInvalidGrant("Refresh token không hợp lệ");
        if (!auth2RefreshToken.isActive())
            throwInvalidGrant("Refresh token đã hết hạn");
    }

    private void throwInvalidGrant(String message) {
        throw new OAuth2AuthenticationException(
                new OAuth2Error(OAuth2ErrorCodes.INVALID_GRANT, message, null)
        );
    }
}
