package ecomhub.productservice.repository;

import ecomhub.productservice.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.UUID;

@Repository
public interface ProductRepository extends MongoRepository<Product, UUID>, ProductRepositoryCustom {
    Page<Product> findByCategoryId(UUID categoryId, Pageable pageable);
}