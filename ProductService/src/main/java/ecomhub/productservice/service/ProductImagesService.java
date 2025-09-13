package ecomhub.productservice.service;

import ecomhub.productservice.dto.request.ProductRequest;
import ecomhub.productservice.dto.response.ProductResponse;

import java.util.List;
import java.util.UUID;

public interface ProductImagesService {
    void saveImages(UUID productId, List<ProductRequest.ProductImageDTO> images);
    List<ProductResponse.ProductImageDTO> getImagesByProductId(UUID productId);
    void deleteByProductId(UUID productId);
}