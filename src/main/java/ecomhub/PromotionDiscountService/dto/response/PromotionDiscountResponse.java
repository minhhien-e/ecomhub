package ecomhub.PromotionDiscountService.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
public class PromotionDiscountResponse {
    private UUID id;
    private String name;
    private String code;
    private String description;
    private String discountType;
    private Double discountValue;
    private Instant startDate;
    private Instant endDate;
    private String status;
    private Instant createdAt;
    private Instant updatedAt;
    private Double originalPrice;
    private Double discountedPrice;
}