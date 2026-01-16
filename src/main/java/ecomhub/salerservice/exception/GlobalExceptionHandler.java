package ecomhub.salerservice.exception;

import ecomhub.salerservice.dto.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpStatusCodeException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(SalerException.class)
    public ResponseEntity<ApiResponse<Void>> handleRuntimeException(SalerException ex) {
        return ResponseEntity.badRequest().body(ApiResponse.error(400,ex.getErrorCode().getCode()));
    }

    @ExceptionHandler(HttpStatusCodeException.class)
    public ResponseEntity<ApiResponse<Void>> handleRestClientException(HttpStatusCodeException ex) {
        return ResponseEntity.status(ex.getStatusCode()).body(ApiResponse.error(ex.getStatusCode().value(), 801));
    }
}
