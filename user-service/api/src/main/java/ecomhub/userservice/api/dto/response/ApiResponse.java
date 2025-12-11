package ecomhub.userservice.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse<T> {
    private int status;
    private T data;
    private String traceId;

    public static <T> ApiResponse<T> success(int status, T data, String traceId) {
        return new ApiResponse<>(status, data, traceId);
    }

    public static <T> ApiResponse<T> error(int status, T data, String traceId) {
        return new ApiResponse<>(status, data, traceId);
    }
}
