package ecomhub.productservice.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class ApiResponse<T> {
    private int statusCode;
    private String errorCode;
    private String message;
    private T data;

    // Phương thức error cho trường hợp không có data
    public static <T> ApiResponse<T> error(int statusCode, String errorCode, String message) {
        return ApiResponse.<T>builder()
                .statusCode(statusCode)
                .errorCode(errorCode)
                .message(message)
                .build();
    }

    // Phương thức error cho trường hợp có Map<String, String> làm data
    public static ApiResponse<Map<String, String>> error(int statusCode, String errorCode, String message, Map<String, String> errors) {
        return ApiResponse.<Map<String, String>>builder()
                .statusCode(statusCode)
                .errorCode(errorCode)
                .message(message)
                .data(errors)
                .build();
    }

    // Phương thức success
    public static <T> ApiResponse<T> success(String message, T data) {
        return ApiResponse.<T>builder()
                .statusCode(200)
                .errorCode(null)
                .message(message)
                .data(data)
                .build();
    }
}