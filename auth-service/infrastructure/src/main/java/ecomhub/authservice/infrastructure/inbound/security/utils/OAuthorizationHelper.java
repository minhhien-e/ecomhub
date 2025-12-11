package ecomhub.authservice.infrastructure.inbound.security.utils;

import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2ErrorCodes;
import org.springframework.security.oauth2.core.OAuth2Token;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;

import static ecomhub.authservice.infrastructure.inbound.security.utils.OAuth2ThrowExceptionHelper.throwException;

public class OAuthorizationHelper {

    public static OAuth2Token getOAuth2Token(OAuth2Authorization authorization, String clientId, String token) throws OAuth2AuthenticationException {
        if (authorization == null)
            throwException("Không tìm thấy thông tin xác thực", OAuth2ErrorCodes.INVALID_TOKEN);
        if (!clientId.equalsIgnoreCase(authorization.getRegisteredClientId()))
            throwException("Client Id không khớp", OAuth2ErrorCodes.INVALID_GRANT);
        OAuth2Authorization.Token<OAuth2Token> oAuth2Token = authorization.getToken(token);
        if (oAuth2Token == null)
            throwException("Token không hợp lệ", OAuth2ErrorCodes.INVALID_GRANT);
        if (!oAuth2Token.isActive())
            throwException("Token đã hết hạn", OAuth2ErrorCodes.INVALID_GRANT);
        return oAuth2Token.getToken();
    }


}
