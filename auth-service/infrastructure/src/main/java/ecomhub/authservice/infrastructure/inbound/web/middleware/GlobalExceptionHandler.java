package ecomhub.authservice.infrastructure.inbound.web.middleware;

import ecomhub.authservice.common.dto.ApiResponse;
import ecomhub.authservice.common.enums.ErrorCode;
import ecomhub.authservice.common.exception.abstracts.HttpException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(HttpException.class)
    public ResponseEntity<?> handleHttpException(HttpException e) {
        var apiResponse = ApiResponse.error(e.getStatusCode(), e.getErrorCode(), e.getMessage());
        return ResponseEntity.status(e.getStatusCode()).body(apiResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception e) {
        var apiResponse = ApiResponse.error(500, ErrorCode.INTERNAL_SERVER_ERROR.name(), e.getMessage());
        return ResponseEntity.status(500).body(apiResponse);
    }
}
