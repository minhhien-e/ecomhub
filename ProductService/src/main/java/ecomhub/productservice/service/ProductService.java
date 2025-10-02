package ecomhub.productservice.service;

import ecomhub.productservice.dto.request.ProductRequest;
import ecomhub.productservice.dto.response.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface ProductService {
    Page<ProductResponse> getAllProducts(Pageable pageable);
    ProductResponse getProductById(UUID id);
    ProductResponse createProduct(ProductRequest request);
    ProductResponse updateProduct(UUID id, ProductRequest request);
    void deleteProduct(UUID id);
    Page<ProductResponse> getProductsByCategory(UUID categoryId, Pageable pageable);
    List<ProductResponse.ProductImageDTO> getProductImages(UUID productId);
    List<ProductResponse.ProductReviewDTO> getProductReviews(UUID productId);
    List<ProductResponse.ProductVariantDTO> getProductVariants(UUID productId);
    void processPurchase(UUID variantId, int quantity);
    void reserveStock(UUID variantId, int quantity);  // Thêm mới
    void unReserveStock(UUID variantId, int quantity);  // Thêm mới
    Page<ProductResponse> filterProducts(Map<String, String> filters, String logic, Pageable pageable);
}