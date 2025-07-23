package ecomhub.authservice.infrastructure.security.converter;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.*;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2ClientAuthenticationToken;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2RefreshTokenAuthenticationToken;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.Map;

public class PublicClientRefreshTokenAuthenticationConverter implements AuthenticationConverter {
    @Override
    public Authentication convert(HttpServletRequest request) {
        String grantType = request.getParameter(OAuth2ParameterNames.GRANT_TYPE);
        if (!AuthorizationGrantType.REFRESH_TOKEN.getValue().equals(grantType))
            throwInvalidGrant("Yêu cầu không hợp lệ", OAuth2ErrorCodes.INVALID_REQUEST);
        MultiValueMap<String, String> parameters = getParameters(request);
        String clientId = getClientId(parameters);
        String refreshToken = getRefreshToken(parameters);
        return new OAuth2RefreshTokenAuthenticationToken(refreshToken, createClientAuthentication(clientId), null, null);

    }

    private Authentication createClientAuthentication(String clientId) {
        return new OAuth2ClientAuthenticationToken(clientId, ClientAuthenticationMethod.NONE, null, Collections.emptyMap());
    }

    private String getRefreshToken(MultiValueMap<String, String> parameters) {
        String refreshToken = parameters.getFirst(OAuth2ParameterNames.REFRESH_TOKEN);
        if (!StringUtils.hasText(refreshToken) || parameters.get(OAuth2ParameterNames.REFRESH_TOKEN).size() != 1) {
            throwInvalidGrant("Thông tin refresh token không hợp lệ", OAuth2ErrorCodes.INVALID_REQUEST);
        }
        parameters.remove(OAuth2ParameterNames.REFRESH_TOKEN);
        return refreshToken;
    }

    private String getClientId(MultiValueMap<String, String> parameters) {
        String clientId = parameters.getFirst(OAuth2ParameterNames.CLIENT_ID);
        if (!StringUtils.hasText(clientId) || parameters.get(OAuth2ParameterNames.CLIENT_ID).size() != 1) {
            throwInvalidGrant("Thông tin clientID không hợp lệ", OAuth2ErrorCodes.INVALID_REQUEST);
        }
        parameters.remove(OAuth2ParameterNames.CLIENT_ID);
        return clientId;
    }

    private MultiValueMap<String, String> getParameters(HttpServletRequest request) {
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        for (Map.Entry<String, String[]> entry : request.getParameterMap().entrySet()) {
            for (String value : entry.getValue()) {
                parameters.add(entry.getKey(), value);
            }
        }
        return parameters;
    }

    private void throwInvalidGrant(String message, String code) throws OAuth2AuthenticationException {
        throw new OAuth2AuthenticationException(
                new OAuth2Error(code, message, null)
        );
    }
}
