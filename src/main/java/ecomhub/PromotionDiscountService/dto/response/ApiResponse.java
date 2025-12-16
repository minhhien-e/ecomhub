package ecomhub.PromotionDiscountService.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {
    private int statusCode;
    private Integer errorCode;  // Chỉ có khi lỗi (600-699), thành công thì null
    private T data;
}