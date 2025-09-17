package ecomhub.inventoryservice.service;

import ecomhub.inventoryservice.dto.StockUpdateRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

// Cái này là cầu nối để inventory service gọi sang product-service để ca[65 nhật tồn kho nha.
@Service
@AllArgsConstructor
public class ProductServiceClient {
    private final RestTemplate restTemplate;

    public void updateStock(UUID variantId, UUID shopId, int quantityChange){
        String url = "http://localhost:8080/inventory/product/stock/"+variantId+"/"+shopId;
        StockUpdateRequest request = new StockUpdateRequest();
        restTemplate.put(url,request);
    }
}
