package ecomhub.productservice.mapper;

import ecomhub.productservice.dto.request.ProductRequest;
import ecomhub.productservice.dto.response.ProductResponse;
import ecomhub.productservice.model.Product;
import ecomhub.productservice.model.ProductImages;
import ecomhub.productservice.model.ProductReviews;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class ProductMapper {

    public Product toEntity(ProductRequest request) {
        Product product = new Product();
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setCategoryId(UUID.fromString(request.getCategoryId()));
        product.setPrice(request.getPrice());
        product.setStatus(request.getStatus());

        if (request.getVariants() != null) {
            List<Product.ProductVariant> variants = request.getVariants().stream()
                    .map(this::toProductVariantEntity)
                    .collect(Collectors.toList());

            for (Product.ProductVariant variant : variants) {
                variant.setId(UUID.randomUUID());
                variant.setCreatedAt(Instant.now());
                variant.setUpdatedAt(Instant.now());
            }
            product.setVariants(variants);
        }
        return product;
    }

    public void merge(Product target, ProductRequest request) {
        // Merge thay vì overwrite toàn bộ để tránh mất dữ liệu
        target.setName(request.getName());
        target.setDescription(request.getDescription());
        target.setCategoryId(UUID.fromString(request.getCategoryId()));
        target.setPrice(request.getPrice());
        target.setStatus(request.getStatus());
        // Variants: nếu request có gửi, ghi đè danh sách (tuỳ business)
        if (request.getVariants() != null) {
            List<Product.ProductVariant> variants = request.getVariants().stream()
                    .map(this::toProductVariantEntity)
                    .collect(Collectors.toList());
            for (Product.ProductVariant variant : variants) {
                variant.setId(UUID.randomUUID());
                variant.setCreatedAt(Instant.now());
                variant.setUpdatedAt(Instant.now());
            }
            target.setVariants(variants);
        }
    }

    public ProductResponse toResponseDTO(
            Product product,
            List<ProductResponse.ProductImageDTO> images,
            List<ProductResponse.ProductReviewDTO> reviews
    ) {
        ProductResponse response = new ProductResponse();
        response.setId(product.getId().toString());
        response.setName(product.getName());
        response.setDescription(product.getDescription());
        response.setCategoryId(product.getCategoryId().toString());
        response.setPrice(product.getPrice());
        response.setStatus(product.getStatus());
        response.setCreatedAt(product.getCreatedAt());
        response.setUpdatedAt(product.getUpdatedAt());
        response.setImages(images);
        response.setReviews(reviews);
        response.setVariants(product.getVariants() != null ? product.getVariants().stream()
                .map(this::toProductVariantDTO)
                .collect(Collectors.toList()) : Collections.emptyList());
        return response;
    }

    public ProductImages toProductImageEntity(ProductRequest.ProductImageDTO imageDto) {
        ProductImages image = new ProductImages();
        image.setUri(imageDto.getUri());
        return image;
    }

    public ProductReviews toProductReviewEntity(ProductRequest.ProductReviewDTO reviewDto, UUID userIdFromToken) {
        ProductReviews review = new ProductReviews();
        // Ưu tiên user từ token; nếu không có thì fallback theo DTO (optional)
        UUID uid = userIdFromToken != null
                ? userIdFromToken
                : (reviewDto.getUserId() != null ? UUID.fromString(reviewDto.getUserId()) : null);
        review.setUserId(uid);
        review.setRating(reviewDto.getRating());
        return review;
    }

    public ProductResponse.ProductImageDTO toProductImageDTO(ProductImages image) {
        ProductResponse.ProductImageDTO imageDto = new ProductResponse.ProductImageDTO();
        imageDto.setId(image.getId().toString());
        imageDto.setUri(image.getUri());
        imageDto.setCreatedAt(image.getCreatedAt());
        return imageDto;
    }

    public ProductResponse.ProductReviewDTO toProductReviewDTO(ProductReviews review) {
        ProductResponse.ProductReviewDTO reviewDto = new ProductResponse.ProductReviewDTO();
        reviewDto.setId(review.getId().toString());
        reviewDto.setUserId(review.getUserId() != null ? review.getUserId().toString() : null);
        reviewDto.setRating(review.getRating());
        reviewDto.setCreatedAt(review.getCreatedAt());
        return reviewDto;
    }

    public Product.ProductVariant toProductVariantEntity(ProductRequest.ProductVariantDTO variantDto) {
        Product.ProductVariant variant = new Product.ProductVariant();
        variant.setSku(variantDto.getSku());
        variant.setPrice(variantDto.getPrice());
        variant.setStock(variantDto.getStock());
        variant.setReserveStock(0);  // Mặc định 0 khi tạo mới
        variant.setVariantStatus(variantDto.getVariantStatus());
        return variant;
    }

    public ProductResponse.ProductVariantDTO toProductVariantDTO(Product.ProductVariant variant) {
        ProductResponse.ProductVariantDTO variantDto = new ProductResponse.ProductVariantDTO();
        variantDto.setId(variant.getId().toString());
        variantDto.setSku(variant.getSku());
        variantDto.setPrice(variant.getPrice());
        variantDto.setAvailableStock(variant.getAvailableStock());
        variantDto.setVariantStatus(variant.getVariantStatus());
        variantDto.setCreatedAt(variant.getCreatedAt());
        variantDto.setUpdatedAt(variant.getUpdatedAt());
        return variantDto;
    }
}