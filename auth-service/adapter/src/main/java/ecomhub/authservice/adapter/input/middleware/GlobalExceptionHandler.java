package ecomhub.authservice.adapter.input.middleware;

import ecomhub.authservice.common.dto.ApiResponse;
import ecomhub.authservice.common.exception.HttpException;
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
}
