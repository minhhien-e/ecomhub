package ecomhub.userservice.infrastructure.exception.mapper;

import ecomhub.userservice.domain.enums.DomainErrorCode;
import ecomhub.userservice.domain.exception.base.DomainException;
import ecomhub.userservice.infrastructure.exception.HttpException;
import org.springframework.stereotype.Component;

@Component
public class DomainToHttpExceptionMapper {

    public HttpException map(DomainException exception) {
        DomainErrorCode domainErrorCode = exception.getErrorCode();

        int statusCode = switch (domainErrorCode) {
            case USER_NOT_FOUND, USER_SETTING_NOT_FOUND -> 404;
            case ACCESS_DENIED -> 403;
            case USER_ALREADY_EXISTS, INVALID_EMAIL, INVALID_USERNAME, INVALID_PASSWORD -> 400;
            default -> 500;
        };

        return new HttpException(exception.getMessage(), statusCode, domainErrorCode.getValue());
    }
}
