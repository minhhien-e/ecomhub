package ecomhub.salerservice.mapper;


import ecomhub.salerservice.dto.response.ShopResponse;
import ecomhub.salerservice.model.Shop;
import org.springframework.stereotype.Component;

@Component // Đánh dấu để Spring quản lý (Bean)
public class ShopMapper {

    public ShopResponse toResponse(Shop shop) {
        if (shop == null) return null;

        return ShopResponse.builder()
                .id(shop.getId())
                .name(shop.getName())
                .bannerUrl(shop.getBannerUrl())
                .contact(shop.getContact())
                .email(shop.getEmail())
                .phone(shop.getPhone())
                .address(shop.getAddress())
                .status(shop.getStatus() != null ? shop.getStatus().name() : null)
                .createdAt(shop.getCreatedAt())
                .updatedAt(shop.getUpdatedAt())
                .build();
    }
}