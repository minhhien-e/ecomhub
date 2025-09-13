package ecomhub.productservice.controller;

import ecomhub.productservice.dto.request.ProductRequest;
import ecomhub.productservice.dto.response.ApiResponse;
import ecomhub.productservice.dto.response.ProductResponse;
import ecomhub.productservice.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PreAuthorize("hasAuthority('role.read')")
    @GetMapping
    public ResponseEntity<ApiResponse<Page<ProductResponse>>> getAllProducts(@PageableDefault(page = 0, size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<ProductResponse> products = productService.getAllProducts(pageable);
        return ResponseEntity.ok(ApiResponse.success("Success", products));
    }

    @PreAuthorize("hasAuthority('role.read')")
    @GetMapping("/filter")
    public ResponseEntity<ApiResponse<Page<ProductResponse>>> filterProducts(@RequestParam Map<String, String> filters, @RequestParam(defaultValue = "and") String logic, @PageableDefault(page = 0, size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<ProductResponse> products = productService.filterProducts(filters, logic, pageable);
        return ResponseEntity.ok(ApiResponse.success("Success", products));
    }

    @PreAuthorize("hasAuthority('role.read')")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductResponse>> getProductById(@PathVariable String id) {
        ProductResponse product = productService.getProductById(UUID.fromString(id));
        return ResponseEntity.ok(ApiResponse.success("Success", product));
    }

    @PreAuthorize("hasAuthority('role.create')")
    @PostMapping
    public ResponseEntity<ApiResponse<ProductResponse>> createProduct(@Valid @RequestBody ProductRequest request) {
        ProductResponse product = productService.createProduct(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("Created successfully", product));
    }

    @PreAuthorize("hasAuthority('role.update')")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductResponse>> updateProduct(@PathVariable String id, @Valid @RequestBody ProductRequest request) {
        ProductResponse product = productService.updateProduct(UUID.fromString(id), request);
        return ResponseEntity.ok(ApiResponse.success("Success", product));
    }

    @PreAuthorize("hasAuthority('role.delete')")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteProduct(@PathVariable String id) {
        productService.deleteProduct(UUID.fromString(id));
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(ApiResponse.success("Deleted successfully", null));
    }

    @PreAuthorize("hasAuthority('role.read')")
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<ApiResponse<Page<ProductResponse>>> getProductsByCategory(@PathVariable String categoryId, @PageableDefault(page = 0, size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<ProductResponse> products = productService.getProductsByCategory(UUID.fromString(categoryId), pageable);
        return ResponseEntity.ok(ApiResponse.success("Success", products));
    }

    @PreAuthorize("hasAuthority('role.read')")
    @GetMapping("/{id}/images")
    public ResponseEntity<ApiResponse<List<ProductResponse.ProductImageDTO>>> getProductImages(@PathVariable String id) {
        List<ProductResponse.ProductImageDTO> images = productService.getProductImages(UUID.fromString(id));
        return ResponseEntity.ok(ApiResponse.success("Success", images));
    }

    @PreAuthorize("hasAuthority('role.read')")
    @GetMapping("/{id}/reviews")
    public ResponseEntity<ApiResponse<List<ProductResponse.ProductReviewDTO>>> getProductReviews(@PathVariable String id) {
        List<ProductResponse.ProductReviewDTO> reviews = productService.getProductReviews(UUID.fromString(id));
        return ResponseEntity.ok(ApiResponse.success("Success", reviews));
    }

    @PreAuthorize("hasAuthority('role.read')")
    @GetMapping("/{id}/variants")
    public ResponseEntity<ApiResponse<List<ProductResponse.ProductVariantDTO>>> getProductVariants(@PathVariable String id) {
        List<ProductResponse.ProductVariantDTO> variants = productService.getProductVariants(UUID.fromString(id));
        return ResponseEntity.ok(ApiResponse.success("Success", variants));
    }

    @PreAuthorize("hasAuthority('role.purchase')")
    @PostMapping("/{variantId}/purchase")
    public ResponseEntity<ApiResponse<Void>> processPurchase(@PathVariable String variantId, @RequestParam int quantity) {
        productService.processPurchase(UUID.fromString(variantId), quantity);
        return ResponseEntity.ok(ApiResponse.success("Success", null));
    }

    @PreAuthorize("hasAuthority('role.reserve')")
    @PostMapping("/{variantId}/reserve")
    public ResponseEntity<ApiResponse<Void>> reserveStock(@PathVariable String variantId, @RequestParam int quantity) {
        productService.reserveStock(UUID.fromString(variantId), quantity);
        return ResponseEntity.ok(ApiResponse.success("Reserved successfully", null));
    }

    @PreAuthorize("hasAuthority('role.reserve')")
    @PostMapping("/{variantId}/unreserve")
    public ResponseEntity<ApiResponse<Void>> unreserveStock(@PathVariable String variantId, @RequestParam int quantity) {
        productService.unreserveStock(UUID.fromString(variantId), quantity);
        return ResponseEntity.ok(ApiResponse.success("Unreserved successfully", null));
    }
}