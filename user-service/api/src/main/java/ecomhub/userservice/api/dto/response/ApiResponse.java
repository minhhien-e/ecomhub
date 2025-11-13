package ecomhub.userservice.api.dto.response;

public record ApiResponse<T>(int statusCode, Integer errorCode, T data) {
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(200, null, data);
    }

    public static <T> ApiResponse<T> success(int statusCode, T data) {
        return new ApiResponse<>(200, null, data);
    }

    public static <T> ApiResponse<T> error(int errorCode) {
        return new ApiResponse<>(500, errorCode, null);
    }
}
