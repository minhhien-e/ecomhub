package com.feature.cartservice.exception.handler;

import com.feature.cartservice.enums.ErrorCode;
import com.feature.cartservice.exception.custom.ExternalApiException;
import com.feature.cartservice.exception.custom.HttpException;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(HttpException.class)
    public ResponseEntity<Map<String, Object>> handleHttpException(HttpException ex) {

        Map<String, Object> response = new HashMap<>();
        int responseStatusCode;
        int responseErrorCode;

        if (ex instanceof ExternalApiException externalEx) {
            log.error(
                    "ExternalApiException: [Internal Error: {} - {}] | [External Details: Status={}, Code={}]",
                    externalEx.getErrorCode(), externalEx.getErrorMessage(),
                    externalEx.getExternalHttpStatusCode(),
                    externalEx.getExternalErrorCode()
            );

            responseStatusCode = externalEx.getExternalHttpStatusCode();
            responseErrorCode = externalEx.getExternalErrorCode();

            response.put("message", ex.getMessage());

        } else {
            log.warn("HttpException: {} - {}", ex.getErrorCode(), ex.getMessage());

            responseStatusCode = ex.getStatusCode();
            responseErrorCode = ex.getErrorCode();
        }

        response.put("statusCode", responseStatusCode);
        response.put("errorCode", responseErrorCode);

        return ResponseEntity.status(responseStatusCode).body(response);
    }

    // Xử lý lỗi @Valid trong body
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationErrors(MethodArgumentNotValidException ex) {
        log.warn("Validation error: {}", ex.getMessage());

        Map<String, Object> response = new HashMap<>();
        response.put("statusCode", HttpStatus.BAD_REQUEST.value());
        response.put("errorCode", ErrorCode.INVALID_FORMAT.getCode());

        Map<String, String> fieldErrors = new HashMap<>();
        return ResponseEntity.badRequest().body(response);
    }

    // Xử lý lỗi @PathVariable, @RequestParam
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, Object>> handleConstraintViolation(ConstraintViolationException ex) {
        log.warn("Constraint violation: {}", ex.getMessage());
        Map<String, Object> response = new HashMap<>();
        response.put("statusCode", HttpStatus.BAD_REQUEST.value());
        response.put("errorCode", ErrorCode.INVALID_FORMAT.getCode());
        return ResponseEntity.badRequest().body(response);
    }

    // Xử lý tất cả các lỗi còn lại
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGeneralError(Exception ex) {
        log.error("Unhandled exception: ", ex);
        Map<String, Object> response = new HashMap<>();
        response.put("statusCode", HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.put("errorCode", ErrorCode.INTERNAL_SERVER_ERROR.getCode());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

}
