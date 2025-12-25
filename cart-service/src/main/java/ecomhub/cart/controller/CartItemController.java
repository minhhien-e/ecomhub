package ecomhub.cart.controller;

import ecomhub.cart.dto.request.AddItemRequest;
import ecomhub.cart.dto.request.UpdateCartItemRequest;
import ecomhub.cart.dto.response.ApiResponse;
import ecomhub.cart.model.Cart;
import ecomhub.cart.model.CustomUser;
//import ecomhub.cartservice.cartservice.service.CreateService;
import ecomhub.cart.service.DeleteService;
import ecomhub.cart.service.ReadService;
import ecomhub.cart.service.UpdateService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@Validated
@RestController
@RequestMapping("/api/v1/cart-item")
public class CartItemController {
//    private final CreateService createService;
    private final UpdateService updateService;
    private final DeleteService deleteService;
    private final ReadService getService;



    @PostMapping("/addItem")
    @PreAuthorize("hasAuthority('role.add_cartitem')")
    public ResponseEntity<?> addItem(@Valid @RequestBody AddItemRequest request, @AuthenticationPrincipal CustomUser user) {
        // quantity , id variants
        double finalPrice = 0.0;
        if (request.price() > 0) {
            finalPrice = request.price();
        }

        Cart cart =updateService.addToCart(
                user.getUserId(),
                request.variantId(),
                request.quantity(),
                finalPrice
        );
        return ResponseEntity.ok(ApiResponse.success(cart, 200));


    }

    @PutMapping("/updateCartItem")
    @PreAuthorize("hasAuthority('role.update_cartitem')")
    public ResponseEntity<?> updateCartItem(@Valid @RequestBody UpdateCartItemRequest request,@AuthenticationPrincipal CustomUser user) {
        updateService.updateCartItem(
                user.getUserId(),
                request.variantId(),
                request.quantity(),
                request.price()
        );
        return ResponseEntity.ok(ApiResponse.success(getService.getCart(user.getUserId()), 200));
    }




    @DeleteMapping("/items/{variantId}/remove")
    @PreAuthorize("hasAuthority('role.remove_cartitem')")
    public ResponseEntity<?> removeItem(@AuthenticationPrincipal CustomUser user, @PathVariable UUID variantId) {
        deleteService.removeItemFromCart(user.getUserId(), variantId);
        return ResponseEntity.ok(ApiResponse.success(getService.getCart(user.getUserId()), 200));
    }


    @GetMapping("items/{variantId}")
    @PreAuthorize("hasAuthority('role.get_cartitem')")
    public ResponseEntity<?> getCartItem(@PathVariable UUID variantId) {
        return ResponseEntity.ok(ApiResponse.success(getService.getProductInfo(variantId), 200));
    }


}
