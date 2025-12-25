package ecomhub.promotiondiscount.exception;

import ecomhub.promotiondiscount.dto.response.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice(basePackages = "ecomhub.PromotionDiscountService")
public class GlobalExceptionHandler {

    @ExceptionHandler(PromotionNotFoundException.class)
    public ResponseEntity<ApiResponse<Object>> handleNotFound(PromotionNotFoundException ex) {
        ApiResponse<Object> response = ApiResponse.builder()
                .statusCode(404)
                .errorCode(ex.getErrorCode())  // 601
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(PromotionValidationException.class)
    public ResponseEntity<ApiResponse<Object>> handleValidationError(PromotionValidationException ex) {
        ApiResponse<Object> response = ApiResponse.builder()
                .statusCode(400)
                .errorCode(ex.getErrorCode())  // 602
                .build();
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Object>> handleBindingErrors(MethodArgumentNotValidException ex) {
        // Có thể log chi tiết lỗi nếu cần, nhưng response chỉ trả errorCode 600
        String details = ex.getBindingResult().getAllErrors()
                .stream()
                .map(err -> ((FieldError) err).getField() + ": " + err.getDefaultMessage())
                .collect(Collectors.joining("; "));
        log.warn("Validation error: {}", details);

        ApiResponse<Object> response = ApiResponse.builder()
                .statusCode(400)
                .errorCode(600)  // 600: Request validation error (@Valid, binding)
                .build();
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>> handleOther(Exception ex) {
        log.error("Unhandled exception", ex);
        ApiResponse<Object> response = ApiResponse.builder()
                .statusCode(500)
                .errorCode(699)  // 699: Internal server error
                .build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}