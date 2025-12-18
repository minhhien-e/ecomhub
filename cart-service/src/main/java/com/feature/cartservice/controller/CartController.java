package com.feature.cartservice.controller;

import com.feature.cartservice.dto.response.ApiResponse;
import com.feature.cartservice.model.Cart;
import com.feature.cartservice.model.CustomUser;
import com.feature.cartservice.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/carts")
@Slf4j
public class CartController {

    private final CreateService createService;
    private final DeleteService deleteService;
    private final ReadService getService;

    @GetMapping("")
    @PreAuthorize("hasAuthority('role.get_cart')")
    public ResponseEntity<?> getCart(@AuthenticationPrincipal CustomUser user) {
        Cart cart = getService.getCart(user.getUserId());
        return ResponseEntity.ok(ApiResponse.success(cart, 200));
    }
// clear all cart items in cart
    @DeleteMapping("/clear_cart")
    @PreAuthorize("hasAuthority('role.clear_cart')")
    public ResponseEntity<?> clearCart(@AuthenticationPrincipal CustomUser user) {
        deleteService.clearCart(user.getUserId());
        return ResponseEntity.ok(ApiResponse.success(null, 200));
    }

// delete cart of user
    @DeleteMapping("/remove_cart")
    @PreAuthorize("hasAuthority('role.delete_cart')")
    public ResponseEntity<?> removeCart(@AuthenticationPrincipal CustomUser user) {
        deleteService.deleteCart(user.getUserId());
        return ResponseEntity.ok(ApiResponse.success(null, 200));
    }
}