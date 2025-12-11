package ecomhub.userservice.infrastructure.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class HttpException extends RuntimeException {
    private final int statusCode;
    private final Integer errorCode;

    public HttpException(String message, int statusCode, Integer errorCode) {
        super(message);
        this.statusCode = statusCode;
        this.errorCode = errorCode;
    }
}
