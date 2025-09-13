package ecomhub.productservice.repository;

import ecomhub.productservice.model.ProductReviews;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.UUID;

public interface ProductReviewsRepository extends MongoRepository<ProductReviews, UUID> {
    List<ProductReviews> findByProductId(UUID productId);
    void deleteByProductId(UUID productId);
}