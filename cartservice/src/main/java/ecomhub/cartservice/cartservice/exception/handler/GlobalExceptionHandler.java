package ecomhub.cartservice.cartservice.exception.handler;

import ecomhub.cartservice.cartservice.dto.response.ApiResponse;
import ecomhub.cartservice.cartservice.enums.ErrorCode;
import ecomhub.cartservice.cartservice.exception.custom.ExternalApiException;
import ecomhub.cartservice.cartservice.exception.custom.HttpException;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(HttpException.class)
    public ResponseEntity<ApiResponse<Object>> handleHttpException(HttpException ex) {
        int responseStatusCode = ex.getStatusCode();
        Integer responseErrorCode = ex.getErrorCode();

        if (ex instanceof ExternalApiException externalEx) {
            log.error("ExternalApiException: [Internal Error: {}] | [External Details: Status={}, Code={}]",
                    externalEx.getErrorCode(),
                    externalEx.getExternalHttpStatusCode(),
                    externalEx.getExternalErrorCode()
            );
            responseStatusCode = externalEx.getExternalHttpStatusCode();
            responseErrorCode = externalEx.getExternalErrorCode();
        } else {
            log.warn("HttpException: {} - {}", ex.getErrorCode(), ex.getMessage());
        }
        return ResponseEntity.status(responseStatusCode)
                .body(new ApiResponse<>(responseStatusCode, responseErrorCode, null));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Object>> handleValidationErrors(MethodArgumentNotValidException ex) {
        log.warn("Validation error: {}", ex.getMessage());

        return ResponseEntity.badRequest()
                .body(ApiResponse.error(HttpStatus.BAD_REQUEST.value(), ErrorCode.INVALID_FORMAT.getCode()));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiResponse<Object>> handleConstraintViolation(ConstraintViolationException ex) {
        log.warn("Constraint violation: {}", ex.getMessage());

        return ResponseEntity.badRequest()
                .body(ApiResponse.error(HttpStatus.BAD_REQUEST.value(), ErrorCode.INVALID_FORMAT.getCode()));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse<Object>> handleIllegalArgument(IllegalArgumentException ex) {
        log.warn("Assert validation failed: {}", ex.getMessage());

        return ResponseEntity.badRequest()
                .body(ApiResponse.error(HttpStatus.BAD_REQUEST.value(), ErrorCode.INVALID_FORMAT.getCode()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>> handleGeneralError(Exception ex) {
        log.error("Unhandled exception: ", ex);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), ErrorCode.INTERNAL_SERVER_ERROR.getCode()));
    }
}