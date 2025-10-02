package ecomhub.productservice.controller;

import ecomhub.productservice.dto.request.ProductRequest;
import ecomhub.productservice.dto.response.ApiResponse;
import ecomhub.productservice.dto.response.ProductResponse;
import ecomhub.productservice.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

import ecomhub.productservice.helper.AuthHelper;  // Import helper mới

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // Helper method: Extract userId từ auth context (flow: request -> converter/auth -> user info)
    private UUID extractCurrentUserId() {
        return AuthHelper.getCurrentUserId().orElseThrow(() -> new RuntimeException("Unauthorized user"));
    }

    // Apply ? cho Page generics: Page<? extends ProductResponse> thay vì full type
    @PreAuthorize("hasAuthority('role.read')")
    @GetMapping
    public ResponseEntity<ApiResponse<Page<? extends ProductResponse>>> getAllProducts(
            @RequestParam(required = false) String categoryId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt,desc") String[] sort) {

        // Converter: Đóng gói request params thành Pageable
        UUID catId = categoryId != null ? UUID.fromString(categoryId) : null;
        Sort.Order order = sort.length > 1 && sort[1].equalsIgnoreCase("desc")
                ? Sort.Order.desc(sort[0]) : Sort.Order.asc(sort[0]);
        Pageable pageable = PageRequest.of(page, size, Sort.by(order));

        Page<? extends ProductResponse> products = catId != null
                ? productService.getProductsByCategory(catId, pageable)
                : productService.getAllProducts(pageable);
        return ResponseEntity.ok(ApiResponse.success("Success", products));
    }

    @PreAuthorize("hasAuthority('role.read')")
    @GetMapping("/filter")
    public ResponseEntity<ApiResponse<Page<? extends ProductResponse>>> filterProducts(
            @RequestParam Map<String, String> filters,
            @RequestParam(defaultValue = "and") String logic,
            @PageableDefault(page = 0, size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {

        // Converter: Validate/đóng gói filters (thêm logic nếu cần, ví dụ check required keys)
        if (filters.isEmpty()) {
            throw new IllegalArgumentException("Filters cannot be empty");
        }
        Page<? extends ProductResponse> products = productService.filterProducts(filters, logic, pageable);
        return ResponseEntity.ok(ApiResponse.success("Success", products));
    }

    @PreAuthorize("hasAuthority('role.read')")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductResponse>> getProductById(@PathVariable UUID id) {
        // Converter: Validate id từ path
        ProductResponse product = productService.getProductById(id);
        return ResponseEntity.ok(ApiResponse.success("Success", product));
    }

    @PreAuthorize("hasAuthority('role.create')")
    @PostMapping
    public ResponseEntity<ApiResponse<ProductResponse>> createProduct(@Valid @RequestBody ProductRequest request) {
        // Converter: Extract creator từ auth (flow: request body -> auth context -> userId)
        UUID creatorId = extractCurrentUserId();
        // TODO: Pass creatorId vào service nếu cần audit (ví dụ set createdBy)
        ProductResponse product = productService.createProduct(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Created successfully", product));
    }

    @PreAuthorize("hasAuthority('role.update')")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductResponse>> updateProduct(@PathVariable UUID id,
                                                                      @Valid @RequestBody ProductRequest request) {
        // Converter: Merge request với existing (service handle)
        UUID updaterId = extractCurrentUserId();
        // TODO: Log updaterId nếu cần
        ProductResponse product = productService.updateProduct(id, request);
        return ResponseEntity.ok(ApiResponse.success("Updated successfully", product));
    }

    @PreAuthorize("hasAuthority('role.delete')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable UUID id) {
        // Converter: Check ownership nếu cần (extract userId so sánh với product owner)
        UUID deleterId = extractCurrentUserId();
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAuthority('role.read')")
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<ApiResponse<Page<? extends ProductResponse>>> getProductsByCategory(
            @PathVariable UUID categoryId,
            @PageableDefault(page = 0, size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {

        Page<? extends ProductResponse> products = productService.getProductsByCategory(categoryId, pageable);
        return ResponseEntity.ok(ApiResponse.success("Success", products));
    }

    @PreAuthorize("hasAuthority('role.read')")
    @GetMapping("/{id}/images")
    public ResponseEntity<ApiResponse<List<?>>> getProductImages(@PathVariable UUID id) {  // ? cho List<ProductImageDTO>
        List<?> images = productService.getProductImages(id);
        return ResponseEntity.ok(ApiResponse.success("Success", images));
    }

    @PreAuthorize("hasAuthority('role.read')")
    @GetMapping("/{id}/reviews")
    public ResponseEntity<ApiResponse<List<?>>> getProductReviews(@PathVariable UUID id) {  // Tương tự cho reviews
        List<?> reviews = productService.getProductReviews(id);
        return ResponseEntity.ok(ApiResponse.success("Success", reviews));
    }

    @PreAuthorize("hasAuthority('role.read')")
    @GetMapping("/{id}/variants")
    public ResponseEntity<ApiResponse<List<?>>> getProductVariants(@PathVariable UUID id) {  // Cho variants
        List<?> variants = productService.getProductVariants(id);
        return ResponseEntity.ok(ApiResponse.success("Success", variants));
    }

    @PreAuthorize("hasAuthority('role.purchase')")
    @PostMapping("/{variantId}/purchase")
    public ResponseEntity<ApiResponse<Void>> processPurchase(@PathVariable UUID variantId,
                                                             @RequestParam int quantity) {
        // Converter: Validate quantity > 0
        if (quantity <= 0) throw new IllegalArgumentException("Quantity must be positive");
        productService.processPurchase(variantId, quantity);
        return ResponseEntity.ok(ApiResponse.success("Purchased successfully", null));
    }

    @PreAuthorize("hasAuthority('role.reserve')")
    @PostMapping("/{variantId}/reserve")
    public ResponseEntity<ApiResponse<Void>> reserveStock(@PathVariable UUID variantId,
                                                          @RequestParam int quantity) {
        if (quantity <= 0) throw new IllegalArgumentException("Quantity must be positive");
        productService.reserveStock(variantId, quantity);
        return ResponseEntity.ok(ApiResponse.success("Reserved successfully", null));
    }

    @PreAuthorize("hasAuthority('role.reserve')")
    @PostMapping("/{variantId}/unreserve")
    public ResponseEntity<ApiResponse<Void>> unReserveStock(@PathVariable UUID variantId,
                                                            @RequestParam int quantity) {
        if (quantity <= 0) throw new IllegalArgumentException("Quantity must be positive");
        productService.unReserveStock(variantId, quantity);
        return ResponseEntity.ok(ApiResponse.success("Unreserved successfully", null));
    }
}
//@RestController
//@RequestMapping("/products")
//public class ProductController {
//    private final ProductService productService;
//
//    public ProductController(ProductService productService) {
//        this.productService = productService;
//    }
//
//    @GetMapping
//    public ResponseEntity<ApiResponse<Page<ProductResponse>>> getAllProducts(
//            @RequestParam(defaultValue = "0") int page,
//            @RequestParam(defaultValue = "10") int size,
//            @RequestParam(defaultValue = "createdAt,desc") String[] sort) {
//
//        Sort.Order order = sort.length > 1 && sort[1].equalsIgnoreCase("desc")
//                ? Sort.Order.desc(sort[0])
//                : Sort.Order.asc(sort[0]);
//
//        Pageable pageable = PageRequest.of(page, size, Sort.by(order));
//        Page<ProductResponse> products = productService.getAllProducts(pageable);
//
//        return ResponseEntity.ok(ApiResponse.success("Success", products));
//    }
//
//    @GetMapping("/filter")
//    public ResponseEntity<ApiResponse<Page<ProductResponse>>> filterProducts(
//            @RequestParam Map<String, String> filters,
//            @RequestParam(defaultValue = "and") String logic,
//            @PageableDefault(page = 0, size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
//
//        Page<ProductResponse> products = productService.filterProducts(filters, logic, pageable);
//        return ResponseEntity.ok(ApiResponse.success("Success", products));
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<ApiResponse<ProductResponse>> getProductById(@PathVariable UUID id) {
//        ProductResponse product = productService.getProductById(id);
//        return ResponseEntity.ok(ApiResponse.success("Success", product));
//    }
//
//    @PostMapping
//    public ResponseEntity<ApiResponse<ProductResponse>> createProduct(@Valid @RequestBody ProductRequest request) {
//        ProductResponse product = productService.createProduct(request);
//        return ResponseEntity.status(HttpStatus.CREATED)
//                .body(ApiResponse.success("Created successfully", product));
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<ApiResponse<ProductResponse>> updateProduct(@PathVariable UUID id,
//                                                                      @Valid @RequestBody ProductRequest request) {
//        ProductResponse product = productService.updateProduct(id, request);
//        return ResponseEntity.ok(ApiResponse.success("Updated successfully", product));
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteProduct(@PathVariable UUID id) {
//        productService.deleteProduct(id);
//        return ResponseEntity.noContent().build();
//    }
//
//    @GetMapping("/category/{categoryId}")
//    public ResponseEntity<ApiResponse<Page<ProductResponse>>> getProductsByCategory(
//            @PathVariable UUID categoryId,
//            @PageableDefault(page = 0, size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
//
//        Page<ProductResponse> products = productService.getProductsByCategory(categoryId, pageable);
//        return ResponseEntity.ok(ApiResponse.success("Success", products));
//    }
//
//    @GetMapping("/{id}/images")
//    public ResponseEntity<ApiResponse<List<ProductResponse.ProductImageDTO>>> getProductImages(@PathVariable UUID id) {
//        List<ProductResponse.ProductImageDTO> images = productService.getProductImages(id);
//        return ResponseEntity.ok(ApiResponse.success("Success", images));
//    }
//
//    @GetMapping("/{id}/reviews")
//    public ResponseEntity<ApiResponse<List<ProductResponse.ProductReviewDTO>>> getProductReviews(@PathVariable UUID id) {
//        List<ProductResponse.ProductReviewDTO> reviews = productService.getProductReviews(id);
//        return ResponseEntity.ok(ApiResponse.success("Success", reviews));
//    }
//
//    @GetMapping("/{id}/variants")
//    public ResponseEntity<ApiResponse<List<ProductResponse.ProductVariantDTO>>> getProductVariants(@PathVariable UUID id) {
//        List<ProductResponse.ProductVariantDTO> variants = productService.getProductVariants(id);
//        return ResponseEntity.ok(ApiResponse.success("Success", variants));
//    }
//
//    @PostMapping("/{variantId}/purchase")
//    public ResponseEntity<ApiResponse<Void>> processPurchase(@PathVariable UUID variantId,
//                                                             @RequestParam int quantity) {
//        productService.processPurchase(variantId, quantity);
//        return ResponseEntity.ok(ApiResponse.success("Purchased successfully", null));
//    }
//
//    @PostMapping("/{variantId}/reserve")
//    public ResponseEntity<ApiResponse<Void>> reserveStock(@PathVariable UUID variantId,
//                                                          @RequestParam int quantity) {
//        productService.reserveStock(variantId, quantity);
//        return ResponseEntity.ok(ApiResponse.success("Reserved successfully", null));
//    }
//
//    @PostMapping("/{variantId}/unreserve")
//    public ResponseEntity<ApiResponse<Void>> unReserveStock(@PathVariable UUID variantId,
//                                                            @RequestParam int quantity) {
//        productService.unReserveStock(variantId, quantity);
//        return ResponseEntity.ok(ApiResponse.success("Unreserved successfully", null));
//    }
//}

//@RestController
//@RequestMapping("/products")
//public class ProductController {
//    private final ProductService productService;
//
//    public ProductController(ProductService productService) {
//        this.productService = productService;
//    }
//
//    @PreAuthorize("hasAuthority('role.read')")
//    @GetMapping
//    public ResponseEntity<ApiResponse<Page<ProductResponse>>> getAllProducts(
//            @RequestParam(required = false) String categoryId,
//            @RequestParam(defaultValue = "0") int page,
//            @RequestParam(defaultValue = "10") int size,
//            @RequestParam(defaultValue = "createdAt,desc") String[] sort) {
//        Sort.Order order = sort.length > 1 && sort[1].equalsIgnoreCase("desc") ?
//                Sort.Order.desc(sort[0]) : Sort.Order.asc(sort[0]);
//        Pageable pageable = PageRequest.of(page, size, Sort.by(order));
//        Page<ProductResponse> products = productService.getProductsByCategory(UUID.fromString(categoryId), pageable);
//        return ResponseEntity.ok(ApiResponse.success("Success", products));
//    }
//
//    @PreAuthorize("hasAuthority('role.read')")
//    @GetMapping("/filter")
//    public ResponseEntity<ApiResponse<Page<ProductResponse>>> filterProducts(@RequestParam Map<String, String> filters, @RequestParam(defaultValue = "and") String logic, @PageableDefault(page = 0, size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
//        Page<ProductResponse> products = productService.filterProducts(filters, logic, pageable);
//        return ResponseEntity.ok(ApiResponse.success("Success", products));
//    }
//
//    @PreAuthorize("hasAuthority('role.read')")
//    @GetMapping("/{id}")
//    public ResponseEntity<ApiResponse<ProductResponse>> getProductById(@PathVariable String id) {
//        ProductResponse product = productService.getProductById(UUID.fromString(id));
//        return ResponseEntity.ok(ApiResponse.success("Success", product));
//    }
//
//    @PreAuthorize("hasAuthority('role.create')")
//    @PostMapping
//    public ResponseEntity<ApiResponse<ProductResponse>> createProduct(@Valid @RequestBody ProductRequest request) {
//        ProductResponse product = productService.createProduct(request);
//        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("Created successfully", product));
//    }
//
//    @PreAuthorize("hasAuthority('role.update')")
//    @PutMapping("/{id}")
//    public ResponseEntity<ApiResponse<ProductResponse>> updateProduct(@PathVariable String id, @Valid @RequestBody ProductRequest request) {
//        ProductResponse product = productService.updateProduct(UUID.fromString(id), request);
//        return ResponseEntity.ok(ApiResponse.success("Success", product));
//    }
//
//    @PreAuthorize("hasAuthority('role.delete')")
//    @DeleteMapping("/{id}")
//    public ResponseEntity<ApiResponse<Void>> deleteProduct(@PathVariable String id) {
//        productService.deleteProduct(UUID.fromString(id));
//        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(ApiResponse.success("Deleted successfully", null));
//    }
//
//    @PreAuthorize("hasAuthority('role.read')")
//    @GetMapping("/category/{categoryId}")
//    public ResponseEntity<ApiResponse<Page<ProductResponse>>> getProductsByCategory(@PathVariable String categoryId, @PageableDefault(page = 0, size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
//        Page<ProductResponse> products = productService.getProductsByCategory(UUID.fromString(categoryId), pageable);
//        return ResponseEntity.ok(ApiResponse.success("Success", products));
//    }
//
//    @PreAuthorize("hasAuthority('role.read')")
//    @GetMapping("/{id}/images")
//    public ResponseEntity<ApiResponse<List<ProductResponse.ProductImageDTO>>> getProductImages(@PathVariable String id) {
//        List<ProductResponse.ProductImageDTO> images = productService.getProductImages(UUID.fromString(id));
//        return ResponseEntity.ok(ApiResponse.success("Success", images));
//    }
//
//    @PreAuthorize("hasAuthority('role.read')")
//    @GetMapping("/{id}/reviews")
//    public ResponseEntity<ApiResponse<List<ProductResponse.ProductReviewDTO>>> getProductReviews(@PathVariable String id) {
//        List<ProductResponse.ProductReviewDTO> reviews = productService.getProductReviews(UUID.fromString(id));
//        return ResponseEntity.ok(ApiResponse.success("Success", reviews));
//    }
//
//    @PreAuthorize("hasAuthority('role.read')")
//    @GetMapping("/{id}/variants")
//    public ResponseEntity<ApiResponse<List<ProductResponse.ProductVariantDTO>>> getProductVariants(@PathVariable String id) {
//        List<ProductResponse.ProductVariantDTO> variants = productService.getProductVariants(UUID.fromString(id));
//        return ResponseEntity.ok(ApiResponse.success("Success", variants));
//    }
//
//    @PreAuthorize("hasAuthority('role.purchase')")
//    @PostMapping("/{variantId}/purchase")
//    public ResponseEntity<ApiResponse<Void>> processPurchase(@PathVariable String variantId, @RequestParam int quantity) {
//        productService.processPurchase(UUID.fromString(variantId), quantity);
//        return ResponseEntity.ok(ApiResponse.success("Success", null));
//    }
//
//    @PreAuthorize("hasAuthority('role.reserve')")
//    @PostMapping("/{variantId}/reserve")
//    public ResponseEntity<ApiResponse<Void>> reserveStock(@PathVariable String variantId, @RequestParam int quantity) {
//        productService.reserveStock(UUID.fromString(variantId), quantity);
//        return ResponseEntity.ok(ApiResponse.success("Reserved successfully", null));
//    }
//
//    @PreAuthorize("hasAuthority('role.reserve')")
//    @PostMapping("/{variantId}/unreserve")
//    public ResponseEntity<ApiResponse<Void>> unReserveStock(@PathVariable String variantId, @RequestParam int quantity) {
//        productService.unReserveStock(UUID.fromString(variantId), quantity);
//        return ResponseEntity.ok(ApiResponse.success("Unreserved successfully", null));
//    }
//}