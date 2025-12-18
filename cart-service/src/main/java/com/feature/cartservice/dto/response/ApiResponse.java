package com.feature.cartservice.dto.response;

public record ApiResponse<T>(
        int statusCode,
        Integer errorCode,
        T data
) {
    public static <T> ApiResponse<T> success(T data, int statusCode) {
        return new ApiResponse<>(statusCode, 0, data);
    }

    public static <T> ApiResponse<T> error(int code, Integer err) {
        return new ApiResponse<>(code, err,null);
    }
}