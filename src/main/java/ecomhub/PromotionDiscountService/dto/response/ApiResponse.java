package ecomhub.PromotionDiscountService.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class ApiResponse<T> {
    private int statusCode;
    private int errorCode;
    private T data;
}