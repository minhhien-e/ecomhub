package ecomhub.salerservice.repository;

import ecomhub.salerservice.model.Shop;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;
import java.util.UUID;

public interface ShopRepository extends MongoRepository<Shop,UUID> {
    Optional<Shop> findBySellerId(UUID sellerId);
}
