package ecomhub.salerservice.controller;

import ecomhub.salerservice.dto.request.RegisterShopRequest;
import ecomhub.salerservice.dto.response.ApiResponse;
import ecomhub.salerservice.dto.response.ShopResponse;
import ecomhub.salerservice.mapper.ShopMapper;
import ecomhub.salerservice.model.Product;
import ecomhub.salerservice.model.Shop;
import ecomhub.salerservice.service.ShopService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/shops")
@RequiredArgsConstructor
public class ShopController {
    private final ShopService shopService;
    @PostMapping()
    public ResponseEntity<ApiResponse<ShopResponse>> register(@RequestHeader("X-Seller-Id") UUID sellerId, @RequestBody RegisterShopRequest request){
        ShopResponse response = shopService.registerShop(request, sellerId);
        return ResponseEntity.ok(ApiResponse.success(response, 200));
    }
}
