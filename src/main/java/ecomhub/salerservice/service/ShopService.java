package ecomhub.salerservice.service;


import ecomhub.salerservice.dto.request.RegisterShopRequest;
import ecomhub.salerservice.dto.request.ShopVerificationRequest;
import ecomhub.salerservice.dto.response.ShopResponse;
import ecomhub.salerservice.mapper.ShopMapper;

import ecomhub.salerservice.model.Shop;
import ecomhub.salerservice.repository.ProductRepository;
import ecomhub.salerservice.repository.ShopRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
@RequiredArgsConstructor
public class ShopService {
    private final ShopRepository shopRepository;
//    private final ProductRepository productRepository;
    private final ShopMapper shopMapper;

    //    client
    public ShopResponse registerShop(RegisterShopRequest request, UUID sellerId) {
        Shop newShop = Shop.builder().name(request.getName()).address(request.getAddress()).phone(request.getPhone()).email(request.getEmail()).bannerUrl(request.getBannerUrl()).contact(request.getContact()).sellerId(sellerId).build();
        Shop savedShop = shopRepository.save(newShop);
        return shopMapper.toResponse(savedShop);
    }

//    admin
    public ShopResponse verifyShop(UUID shopId, ShopVerificationRequest request) {
        Shop shop = shopRepository.findById(shopId).orElseThrow(() -> new RuntimeException("Shop not found with id: " + shopId));

        shop.setStatus(request.getStatus());
        Shop savedShop = shopRepository.save(shop);

        return shopMapper.toResponse(savedShop);
    }

//    client
//    public Product addProductToShop(UUID sellerId, AddProductRequest request){
////       tìm cái shop của thằng seller
//        Shop shop = shopRepository.findBySellerId(sellerId).orElseThrow(() -> new RuntimeException("You haven't registered any shop yet!"));
//
////        Shop active thì mới được thêm hàng nha
//        if(shop.getStatus() != Status.ACTIVE){
//            throw new RuntimeException("Your shop has not been approved or has been locked!");
//        }
//
//        boolean exists = productRepository.existsByShopIdAndCoreProductId(shop.getId(), request.getProductId());
//        if(exists){
//            throw new RuntimeException("This product already exists in your shop!");
//        }
//
////        Cái này là nếu request ko gửi isActive thì nó sẽ mặc định là true
//        boolean statusToSave = (request.getIsActive() != null) ? request.getIsActive() : true;
//        Product newProduct = Product.builder().shopId(shop.getId()).productId(request.getProductId()).name(request.getName()).isActive(statusToSave).build();
//
//        return productRepository.save(newProduct);
//
//    }

//    Hiển thị danh sách của người dùng đó
//    public List<UUID> getMySellingProductIds(UUID sellerId){
////        tiếp tục tìm shop của thằng seller
//        Shop shop = shopRepository.findBySellerId(sellerId).orElseThrow(() -> new RuntimeException("You haven't registered any shop yet!"));
//
////        Laấy danh sách product
//        List<Product> myProducts = productRepository.findAllByShopId(shop.getId());
//        return myProducts.stream().map(Product::getProductId).collect(Collectors.toList());
//    }

}
