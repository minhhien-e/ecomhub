package com.ecomhub.cartservice.adapters.input.controller;

import com.ecomhub.cartservice.adapters.input.dto.AddToCartRequest;
import com.ecomhub.cartservice.adapters.input.dto.UpdateCartRequest;
import com.ecomhub.cartservice.adapters.input.dto.CartResponse;
import com.ecomhub.cartservice.application.common.ApiResponse;
import com.ecomhub.cartservice.application.service.CartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    /**
     * Thêm sản phẩm vào giỏ hàng
     */
    @PostMapping("/add")
    public ResponseEntity<ApiResponse<Void>> addToCart(@Valid @RequestBody AddToCartRequest request) {
        cartService.addItemToCart(request.toCartItem(), request.getUserId());
        return ResponseEntity.ok(ApiResponse.Success(null, "Thêm sản phẩm vào giỏ hàng thành công"));
    }

    /**
     * Xóa toàn bộ giỏ hàng của người dùng
     */
    @DeleteMapping("/clear/{userId}")
    public ResponseEntity<ApiResponse<Void>> clearCart(@PathVariable String userId) {
        cartService.clearCart(userId);
        return ResponseEntity.ok(ApiResponse.Success(null, "Đã xóa toàn bộ giỏ hàng"));
    }

    /**
     * Xóa 1 sản phẩm khỏi giỏ hàng theo userId + productId
     */
    @DeleteMapping("/remove/{userId}/{productId}")
    public ResponseEntity<ApiResponse<Void>> removeItem(
            @PathVariable String userId,
            @PathVariable String productId
    ) {
        cartService.removeItem(userId, productId);
        return ResponseEntity.ok(ApiResponse.Success(null, "Đã xóa sản phẩm khỏi giỏ hàng"));
    }

    /**
     * Cập nhật thông tin sản phẩm trong giỏ hàng
     */
    @PutMapping("/update")
    public ResponseEntity<ApiResponse<Void>> updateItem(@Valid @RequestBody UpdateCartRequest request) {
        cartService.updateItem(request.getUserId(), request.toCartItem());
        return ResponseEntity.ok(ApiResponse.Success(null, "Đã cập nhật sản phẩm trong giỏ hàng"));
    }

    /**
     * Lấy giỏ hàng theo userId
     */
    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse<CartResponse>> getCart(@PathVariable String userId) {
        var cart = cartService.getCart(userId);
        var response = CartResponse.from(cart);
        return ResponseEntity.ok(ApiResponse.Success(response, "Lấy giỏ hàng thành công"));
    }
}
