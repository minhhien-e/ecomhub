package ecomhub.productservice.exception;

import com.mongodb.MongoException;
import ecomhub.productservice.dto.response.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleProductNotFound(ProductNotFoundException ex) {
        logger.error("Product not found: {}", ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                ApiResponse.error(HttpStatus.NOT_FOUND.value(), "NOT_FOUND", ex.getMessage())
        );
    }

    @ExceptionHandler(ProductValidationException.class)
    public ResponseEntity<ApiResponse<Void>> handleProductValidation(ProductValidationException ex) {
        logger.error("Validation error: {}", ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                ApiResponse.error(HttpStatus.BAD_REQUEST.value(), "BAD_REQUEST", ex.getMessage())
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Map<String, String>>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        logger.error("Validation errors: {}", errors, ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                ApiResponse.error(HttpStatus.BAD_REQUEST.value(), "BAD_REQUEST", "Invalid input data", errors)
        );
    }

    @ExceptionHandler(MongoException.class)
    public ResponseEntity<ApiResponse<Void>> handleMongoException(MongoException ex) {
        logger.error("MongoDB error: {}", ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "DB_ERROR", "Database error: " + ex.getMessage())
        );
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse<Void>> handleIllegalArgument(IllegalArgumentException ex) {
        logger.error("Illegal argument error: {}", ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                ApiResponse.error(HttpStatus.BAD_REQUEST.value(), "INVALID_INPUT", "Invalid input: " + ex.getMessage())
        );
    }

    @ExceptionHandler(PropertyReferenceException.class)
    public ResponseEntity<ApiResponse<Void>> handlePropertyReferenceException(PropertyReferenceException ex) {
        logger.error("Pageable error: {}", ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                ApiResponse.error(HttpStatus.BAD_REQUEST.value(), "INVALID_PAGEABLE", "Invalid pageable parameters: " + ex.getMessage())
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleUnexpected(Exception ex) {
        logger.error("Unexpected error: {}", ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "INTERNAL_SERVER_ERROR", "Unexpected error: " + ex.getMessage())
        );
    }
}