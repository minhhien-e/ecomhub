package ecomhub.promotiondiscount.util;

import ecomhub.promotiondiscount.dto.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public final class ResponseUtil {
    private ResponseUtil() {}

    public static <T> ResponseEntity<ApiResponse<T>> ok(T data) {
        return ResponseEntity.ok(ApiResponse.<T>builder()
                .statusCode(200)
                .data(data)
                .build());
    }

    public static <T> ResponseEntity<ApiResponse<T>> created(T data) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<T>builder()
                        .statusCode(201)
                        .data(data)
                        .build());
    }

    public static ResponseEntity<ApiResponse<Object>> noContent() {
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body(ApiResponse.builder()
                        .statusCode(204)
                        .build());
    }
}