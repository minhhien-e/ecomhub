package com.ecomhub.cartservice.shared.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Standard API response wrapper")
public class ApiResponse<T> {

    @Schema(description = "HTTP status code")
    private int statusCode;

    @Schema(description = "Error code if any, null when success")
    private String errorCode;

    @Schema(description = "Response message")
    private String message;

    @Schema(description = "Payload data")
    private T data;

    public ApiResponse(int statusCode, String errorCode, String message, T data) {
        this.statusCode = statusCode;
        this.errorCode = errorCode;
        this.message = message;
        this.data = data;
    }

    public static <T> ApiResponse<T> Success(T data, String message) {
        return new ApiResponse<>(200, null, message, data);
    }

    public static <T> ApiResponse<T> Error(int code, String errorCode, String message) {
        return new ApiResponse<>(code, errorCode, message, null);
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }
}
