package ecomhub.authservice.infrastructure.security.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2ClientAuthenticationToken;

import java.util.Collections;

public class PublicClientAuthenticationFactory {
    public static Authentication createClientAuthentication(String clientId) {
        var authentication = new OAuth2ClientAuthenticationToken(clientId, ClientAuthenticationMethod.NONE, null, Collections.emptyMap());
        authentication.setAuthenticated(true);
        return authentication;
    }
}
