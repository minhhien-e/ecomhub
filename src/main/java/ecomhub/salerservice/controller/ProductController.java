package ecomhub.salerservice.controller;

import ecomhub.salerservice.dto.request.ProductCreateRequest;
import ecomhub.salerservice.dto.response.ApiResponse;
import ecomhub.salerservice.model.Product;
import ecomhub.salerservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/api/v1/shops/{shopId}/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    public ResponseEntity<ApiResponse<Product>> addProductToShop(
            @PathVariable UUID shopId,
            @RequestBody ProductCreateRequest request) {

        Product result = productService.createProductForShop(shopId, request);

        return ResponseEntity.ok(ApiResponse.success(result, 200));
    }

    @PutMapping("/{productId}")
    public ResponseEntity<ApiResponse<Product>> update(@PathVariable UUID productId, @PathVariable UUID shopId, ProductCreateRequest request){
        Product result = productService.updateProduct(shopId, productId, request);
        return ResponseEntity.ok(ApiResponse.success(result, 200));
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable UUID shopId, @PathVariable UUID productId){
        productService.deleteProduct(shopId, productId);
        return ResponseEntity.ok(ApiResponse.success(null, 200));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Product>>> getProducts(@PathVariable UUID shopId){
        List<Product> products = productService.getProductsByShop(shopId);
        return ResponseEntity.ok(ApiResponse.success(products, 200));
    }
}

