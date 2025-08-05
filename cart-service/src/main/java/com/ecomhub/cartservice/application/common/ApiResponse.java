package com.ecomhub.cartservice.application.common;

public class ApiResponse<T> {
    private int statusCode;
    private String errorCode;
    private String message;
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
