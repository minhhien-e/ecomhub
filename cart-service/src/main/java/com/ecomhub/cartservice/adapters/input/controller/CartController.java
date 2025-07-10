package com.ecomhub.cartservice.adapters.input.controller;

import com.ecomhub.cartservice.adapters.input.dto.AddToCartRequest;
import com.ecomhub.cartservice.application.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;

    @PostMapping("/add")
    public ResponseEntity<Void> addToCart(@RequestBody AddToCartRequest request) {
        cartService.addItemToCart(request.toCartItem(), request.getUserId());
        return ResponseEntity.ok().build();
    }

}
