package com.ecomhub.cartservice.adapters.input.controller;

import com.ecomhub.cartservice.adapters.input.dto.AddToCartRequest;
import com.ecomhub.cartservice.adapters.input.dto.UpdateCartRequest;
import com.ecomhub.cartservice.application.CartService;
import jakarta.annotation.PostConstruct;
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
    public ResponseEntity<Void> addToCart(@RequestBody AddToCartRequest request) {
        cartService.addItemToCart(request.toCartItem(), request.getUserId());
        return ResponseEntity.ok().build();
    }

    /**
     * Xóa toàn bộ giỏ hàng của người dùng
     */
    @DeleteMapping("/clear/{userId}")
    public ResponseEntity<Void> clearCart(@PathVariable String userId) {
        cartService.clearCart(userId);
        return ResponseEntity.ok().build();
    }

    /**
     * Xóa 1 sản phẩm khỏi giỏ hàng theo userId + productId
     */
    @DeleteMapping("/remove/{userId}/{productId}")
    public ResponseEntity<Void> removeItem(
            @PathVariable String userId,
            @PathVariable String productId
    ) {
        cartService.removeItem(userId, productId);
        return ResponseEntity.ok().build();
    }

    /**
     * Cập nhật thông tin sản phẩm trong giỏ hàng
     */
    @PutMapping("/update")
    public ResponseEntity<Void> updateItem(@RequestBody UpdateCartRequest request) {
        cartService.updateItem(request.getUserId(), request.toCartItem());
        return ResponseEntity.ok().build();
    }
}
