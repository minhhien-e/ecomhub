package ecomhub.salerservice.controller;

import ecomhub.salerservice.dto.response.ApiResponse;
import ecomhub.salerservice.dto.response.ShopDetailResponse;
import ecomhub.salerservice.service.ShopService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/public/shops")
@RequiredArgsConstructor
public class PublicShopController {
    private final ShopService shopService;

    @GetMapping("/{shopId}")
    public ResponseEntity<ApiResponse<ShopDetailResponse>> getShopLandingPage(@PathVariable UUID shopId, @RequestParam Pageable pageable){
        ShopDetailResponse response = shopService.getShopDetailPublic(shopId, pageable);
        return ResponseEntity.ok(ApiResponse.success(response, 200));
    }
}
