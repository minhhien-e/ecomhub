package ecomhub.productservice.dto.request;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProductRequest {
    @NotBlank(message = "Name is required")
    private String name;

    private String description;

    @NotBlank(message = "Category ID is required")
    private String categoryId;

    @NotNull(message = "Price is required")
    @Positive(message = "Price must be greater than 0")
    private Double price;

    @NotBlank(message = "Status is required")
    private String status;

    private List<ProductImageDTO> images;
    private List<ProductReviewDTO> reviews;
    private List<ProductVariantDTO> variants;

    @Getter
    @Setter
    public static class ProductImageDTO {
        @NotBlank(message = "Image URI is required")
        private String uri;
    }

    @Getter
    @Setter
    public static class ProductReviewDTO {
        @NotBlank(message = "User ID is required")
        private String userId;

        @NotNull(message = "Rating is required")
        @Min(value = 1, message = "Rating must be at least 1")
        @Max(value = 5, message = "Rating must not exceed 5")
        private Integer rating;
    }

    @Getter
    @Setter
    public static class ProductVariantDTO {
        @NotBlank(message = "SKU is required")
        private String sku;

        @NotNull(message = "Price is required")
        @Positive(message = "Price must be greater than 0")
        private Double price;

        @NotNull(message = "Stock quantity is required")
        @PositiveOrZero(message = "Stock quantity must be 0 or greater")
        private Integer stock;

        @NotBlank(message = "Variant status is required")
        private String variantStatus;
    }
}