package ecomhub.authservice.infrastructure.inbound.security.utils;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.oauth2.core.OAuth2ErrorCodes;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;

import java.util.Map;

import static ecomhub.authservice.infrastructure.inbound.security.utils.OAuth2ThrowExceptionHelper.throwException;

public class OAuth2RequestParameterExtractor {
    public static MultiValueMap<String, String> getParameters(HttpServletRequest request) {
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        for (Map.Entry<String, String[]> entry : request.getParameterMap().entrySet()) {
            for (String value : entry.getValue()) {
                parameters.add(entry.getKey(), value);
            }
        }
        return parameters;
    }

    public static String getValue(MultiValueMap<String, String> parameters, String tokenName, String messageError) {
        String token = parameters.getFirst(tokenName);
        if (!StringUtils.hasText(token) || parameters.get(tokenName).size() != 1) {
            throwException(messageError == null ? "Thông tin không hợp lệ" : messageError
                    , OAuth2ErrorCodes.INVALID_REQUEST);
        }
        parameters.remove(tokenName);
        return token;
    }
}
