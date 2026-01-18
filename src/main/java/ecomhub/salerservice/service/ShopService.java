package ecomhub.salerservice.service;


import ecomhub.salerservice.dto.request.RegisterShopRequest;
import ecomhub.salerservice.dto.request.ShopVerificationRequest;
import ecomhub.salerservice.dto.response.ShopDetailResponse;
import ecomhub.salerservice.dto.response.ShopResponse;
import ecomhub.salerservice.enums.SalerErrorCode;
import ecomhub.salerservice.enums.Status;
import ecomhub.salerservice.exception.SalerException;
import ecomhub.salerservice.mapper.ShopMapper;

import ecomhub.salerservice.model.Product;
import ecomhub.salerservice.model.Shop;
import ecomhub.salerservice.repository.ProductRepository;
import ecomhub.salerservice.repository.ShopRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
@RequiredArgsConstructor
public class ShopService {
    private final ShopRepository shopRepository;
    private final ProductRepository productRepository;
    private final ShopMapper shopMapper;

    private Shop getActiveShopOrThrow(UUID shopId){
        Shop shop = shopRepository.findById(shopId).orElseThrow(() ->new SalerException(SalerErrorCode.SHOP_NOT_FOUND));
        if(shop.getStatus() != Status.ACTIVE){
            throw new SalerException(SalerErrorCode.SHOP_UNAUTHORIZED);
        }
        return shop;
    }

    //    client
    public ShopResponse registerShop(RegisterShopRequest request, UUID sellerId) {
        shopRepository.findBySellerId(sellerId).ifPresent(s -> {throw new SalerException(SalerErrorCode.SHOP_ALDREADY_EXISTS);});

        Shop newShop = Shop.builder().name(request.getName()).address(request.getAddress()).phone(request.getPhone()).email(request.getEmail()).bannerUrl(request.getBannerUrl()).contact(request.getContact()).sellerId(sellerId).build();
        Shop savedShop = shopRepository.save(newShop);
        return shopMapper.toResponse(savedShop);
    }

//    admin
    public ShopResponse verifyShop(UUID shopId, ShopVerificationRequest request) {
        Shop shop = shopRepository.findById(shopId).orElseThrow(() -> new SalerException((SalerErrorCode.SHOP_NOT_FOUND)));

        shop.setStatus(request.getStatus());
        Shop savedShop = shopRepository.save(shop);

        return shopMapper.toResponse(savedShop);
    }


    public Page<Product> getProductsByShop(UUID shopId, Pageable pageable){
        getActiveShopOrThrow(shopId);
        return productRepository.findAllByShopIdAndIsActiveTrue(shopId, pageable);
    }

//    Khi client click vào xem shop thì go hà
    public ShopDetailResponse getShopDetailPublic(UUID shopId, Pageable pageable){
        Shop shop = getActiveShopOrThrow(shopId);

        Page<Product> pageProducts = productRepository.findAllByShopIdAndIsActiveTrue(shopId, pageable);

        return ShopDetailResponse.builder().shopInfo(shopMapper.toResponse(shop)).products(pageProducts).build();
    }
}
