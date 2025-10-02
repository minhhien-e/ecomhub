package ecomhub.productservice.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.AllArgsConstructor;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor          // constructor mặc định public
@AllArgsConstructor         // constructor tất cả field public
@Builder
public class ProductResponse {
    private String id;
    private String name;
    private String description;
    private String categoryId;
    private Double price;
    private String status;
    private List<ProductImageDTO> images;
    private List<ProductReviewDTO> reviews;
    private List<ProductVariantDTO> variants;
    private Instant createdAt;
    private Instant updatedAt;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ProductImageDTO {
        private String id;
        private String uri;
        private Instant createdAt;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ProductReviewDTO {
        private String id;
        private String userId;
        private Integer rating;
        private Instant createdAt;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ProductVariantDTO {
        private String id;
        private String sku;
        private Double price;
        private Integer availableStock;
        private String variantStatus;
        private Instant createdAt;
        private Instant updatedAt;
    }
}
