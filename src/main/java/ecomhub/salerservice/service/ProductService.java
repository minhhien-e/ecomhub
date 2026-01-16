package ecomhub.salerservice.service;

import ecomhub.salerservice.dto.request.ProductCreateRequest;
import ecomhub.salerservice.dto.response.ApiResponse;
import ecomhub.salerservice.dto.response.ExternalProductResponse;
import ecomhub.salerservice.enums.SalerErrorCode;
import ecomhub.salerservice.exception.SalerException;
import ecomhub.salerservice.model.Product;
import ecomhub.salerservice.model.Shop;
import ecomhub.salerservice.repository.ProductRepository;
import ecomhub.salerservice.repository.ShopRepository;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ShopRepository shopRepository;
    private final RestTemplate restTemplate;
    @Value("${external.product-service.url}")
    private String productServiceUrl;

    public Product createProductForShop(UUID shopId, ProductCreateRequest request) {
        Shop shop = shopRepository.findById(request.getShopId()).orElseThrow(() -> new RuntimeException(String.valueOf(SalerErrorCode.SHOP_NOT_FOUND)));

//        exchange, earaser
        ParameterizedTypeReference<ApiResponse<ExternalProductResponse>> typeReference = new ParameterizedTypeReference<ApiResponse<ExternalProductResponse>>() {
        };

        try{
        ResponseEntity<ApiResponse<ExternalProductResponse>> response = restTemplate.exchange(productServiceUrl, HttpMethod.POST, new HttpEntity<>(request), typeReference);

        ApiResponse<ExternalProductResponse> externalApiResponse = response.getBody();
        if (externalApiResponse != null && externalApiResponse.data() != null) {
            ExternalProductResponse data = externalApiResponse.data();

            Product product = new Product();
            product.setProductId(data.getId()); // ID từ service ngoài
            product.setShopId(shopId);
            product.setName(data.getName());
            product.setActive(true);
            return productRepository.save(product);
        }
            throw new SalerException(SalerErrorCode.EXTERNAL_SERVICE_ERROR);
        }catch (Exception e) {
            throw new SalerException(SalerErrorCode.EXTERNAL_SERVICE_ERROR);
        }
    }

    public Product updateProduct(UUID shopId, UUID productId, ProductCreateRequest request){
        Product product = productRepository.findByShopIdAndProductId(shopId, productId).orElseThrow(() -> new SalerException(SalerErrorCode.SHOP_NOT_FOUND));

        String updateUrl = productServiceUrl + "/" + productId;

        try {
            HttpEntity<ProductCreateRequest> entity = new HttpEntity<>(request);
            ParameterizedTypeReference<ApiResponse<ExternalProductResponse>> typeReference = new ParameterizedTypeReference<ApiResponse<ExternalProductResponse>>() {
            };

            ResponseEntity<ApiResponse<ExternalProductResponse>> response = restTemplate.exchange(updateUrl, HttpMethod.PUT, entity, typeReference);

            ApiResponse<ExternalProductResponse> body = response.getBody();

            if (body != null && body.data() != null) {
                product.setName(body.data().getName());


                if (request.getIsActive() != null) {
                    product.setActive(request.getIsActive());
                }

                return productRepository.save(product);
            }
                throw new SalerException(SalerErrorCode.EXTERNAL_SERVICE_ERROR);
        }catch (Exception e){
            throw new SalerException(SalerErrorCode.EXTERNAL_SERVICE_ERROR);
        }

    }

    public void deleteProduct(UUID shopId, UUID productId){
        Product product = productRepository.findByShopIdAndProductId(shopId, productId).orElseThrow(() -> new SalerException(SalerErrorCode.EXTERNAL_SERVICE_ERROR));

        String deleteUrl = productServiceUrl + "/" + productId;

        try {
            restTemplate.exchange(deleteUrl, HttpMethod.DELETE, null, new ParameterizedTypeReference<ApiResponse<Void>>(){});

            product.setActive(false);
            productRepository.save(product);

        }
        catch (Exception e){
            throw new SalerException(SalerErrorCode.EXTERNAL_SERVICE_ERROR);
        }
    }

    public List<Product> getProductsByShop(UUID shopId){
        shopRepository.findById(shopId).orElseThrow(() -> new SalerException(SalerErrorCode.SHOP_NOT_FOUND));

        return productRepository.findAllByShopIdAndIsActiveTrue(shopId);
    }
}
