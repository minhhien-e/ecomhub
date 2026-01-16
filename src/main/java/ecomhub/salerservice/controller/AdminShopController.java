package ecomhub.salerservice.controller;

import ecomhub.salerservice.dto.request.ShopVerificationRequest;
import ecomhub.salerservice.dto.response.ApiResponse;
import ecomhub.salerservice.dto.response.ShopResponse;
import ecomhub.salerservice.service.ShopService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/admin/shops")
@RequiredArgsConstructor
public class AdminShopController {
    private final ShopService shopService;

    @PatchMapping("/{id}/verify")
    public ResponseEntity<ApiResponse<ShopResponse>> verifyShop(@PathVariable UUID id, @RequestBody ShopVerificationRequest request) {
        ShopResponse response = shopService.verifyShop(id, request);
        return ResponseEntity.ok(ApiResponse.success(response, 200));
    }

}
