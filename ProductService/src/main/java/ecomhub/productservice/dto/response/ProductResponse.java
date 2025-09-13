package ecomhub.productservice.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
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
    public static class ProductImageDTO {
        private String id;
        private String uri;
        private Instant createdAt;
    }

    @Getter
    @Setter
    public static class ProductReviewDTO {
        private String id;
        private String userId;
        private Integer rating;
        private Instant createdAt;
    }

    @Getter
    @Setter
    public static class ProductVariantDTO {
        private String id;
        private String sku;
        private Double price;
        private Integer stock;
        private Integer reserveStock;
        private String variantStatus;
        private Instant createdAt;
        private Instant updatedAt;
    }
}