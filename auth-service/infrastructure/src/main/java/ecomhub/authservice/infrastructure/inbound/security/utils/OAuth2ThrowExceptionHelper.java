package ecomhub.authservice.infrastructure.inbound.security.utils;

import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;

public class OAuth2ThrowExceptionHelper {
    public static void throwException(String message, String errorCode) throws OAuth2AuthenticationException {
        throw new OAuth2AuthenticationException(
                new OAuth2Error(errorCode, message, null)
        );
    }
}
