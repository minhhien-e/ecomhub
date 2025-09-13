package ecomhub.productservice.repository;

import ecomhub.productservice.model.ProductImages;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.UUID;

public interface ProductImagesRepository extends MongoRepository<ProductImages, UUID> {
    List<ProductImages> findByProductId(UUID productId);
    void deleteByProductId(UUID productId);
}