package ecomhub.PromotionDiscountService.exception;

import ecomhub.PromotionDiscountService.dto.response.ApiResponse;
import ecomhub.PromotionDiscountService.util.ResponseUtil;
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
        return ResponseUtil.notFound(404);
    }

    @ExceptionHandler(PromotionValidationException.class)
    public ResponseEntity<ApiResponse<Object>> handleValidationError(PromotionValidationException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.builder()
                        .statusCode(400)
                        .errorCode(0)
                        .build());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Object>> handleBindingErrors(MethodArgumentNotValidException ex) {
        String msg = ex.getBindingResult().getAllErrors()
                .stream()
                .map(err -> ((FieldError) err).getField() + ": " + err.getDefaultMessage())
                .collect(Collectors.joining("; "));
        return ResponseUtil.badRequest();
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>> handleOther(Exception ex) {
        log.error("Unhandled exception", ex);
        return ResponseUtil.serverError();
    }
}