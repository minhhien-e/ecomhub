package ecomhub.userservice.api.exception;

import ecomhub.userservice.api.dto.response.ApiResponse;
import ecomhub.userservice.application.port.in.provider.CurrentTraceIdProvider;
import ecomhub.userservice.infrastructure.exception.HttpException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class GlobalExceptionHandler {
    private final CurrentTraceIdProvider currentTraceIdProvider;

    @ExceptionHandler(HttpException.class)
    public ResponseEntity<?> handleHttpException(HttpException e) {
        log.error(e.getMessage());
        return ResponseEntity.status(e.getStatusCode()).body(ApiResponse.error(e.getStatusCode(), e.getErrorCode(), currentTraceIdProvider.getCurrentTraceId()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception e) {
        log.error(e.getMessage());
        return ResponseEntity.status(500).body(ApiResponse.error(500, null, currentTraceIdProvider.getCurrentTraceId()));

    }
}
