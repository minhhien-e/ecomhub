package ecomhub.salerservice.repository;

import ecomhub.salerservice.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductRepository extends MongoRepository<Product, UUID> {
//    boolean existsByShopIdAndCoreProductId(UUID shopId, UUID coreProductId);

    Optional<Product> findByShopIdAndProductId(UUID shopId, UUID productId);

    List<Product> findAllByShopIdAndIsActiveTrue(UUID shopId);
}
