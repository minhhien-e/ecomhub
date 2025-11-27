package ecomhub.PromotionDiscountService.util;

import ecomhub.PromotionDiscountService.dto.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public final class ResponseUtil {
    private ResponseUtil() {}

    public static <T> ResponseEntity<ApiResponse<T>> ok(T data) {
        return ResponseEntity.ok(ApiResponse.<T>builder()
                .statusCode(200)
                .errorCode(0)
                .data(data)
                .build());
    }

    public static <T> ResponseEntity<ApiResponse<T>> created(T data) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<T>builder()
                        .statusCode(201)
                        .errorCode(0)
                        .data(data)
                        .build());
    }

    public static ResponseEntity<ApiResponse<Object>> noContent() {
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body(ApiResponse.builder()
                        .statusCode(204)
                        .errorCode(0)
                        .build());
    }

    public static ResponseEntity<ApiResponse<Object>> badRequest() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.builder()
                        .statusCode(400)
                        .errorCode(0)
                        .build());
    }

    public static ResponseEntity<ApiResponse<Object>> notFound(int code) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.builder()
                        .statusCode(code)
                        .errorCode(0)
                        .build());
    }

    public static ResponseEntity<ApiResponse<Object>> serverError() {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.builder()
                        .statusCode(500)
                        .errorCode(0)
                        .build());
    }
}