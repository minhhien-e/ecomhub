package ecomhub.productservice.service;

import ecomhub.productservice.dto.request.ProductRequest;
import ecomhub.productservice.dto.response.ProductResponse;

import java.util.List;
import java.util.UUID;

public interface ProductReviewsService {
    void saveReviews(UUID productId, List<ProductRequest.ProductReviewDTO> reviews);
    List<ProductResponse.ProductReviewDTO> getReviewsByProductId(UUID productId);
    void deleteByProductId(UUID productId);
}