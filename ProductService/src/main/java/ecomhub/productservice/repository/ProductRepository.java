package ecomhub.productservice.repository;

import ecomhub.productservice.exception.ProductNotFoundException;
import ecomhub.productservice.exception.ProductValidationException;
import ecomhub.productservice.model.Product;
import ecomhub.productservice.repository.specification.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Repository
public interface ProductRepository extends MongoRepository<Product, UUID> {
    Page<Product> findByCategoryId(UUID categoryId, Pageable pageable);

    default Page<Product> findByDynamicFilters(Map<String, String> filters, String logic, Pageable pageable, MongoTemplate mongoTemplate) {
        List<ProductSpecification> specifications = new ArrayList<>();

        filters.forEach((key, value) -> {
            try {
                switch (key.toLowerCase()) {
                    case "name" -> specifications.add(new NameSpecification(value));
                    case "categoryid" -> specifications.add(new CategoryIdSpecification(UUID.fromString(value)));
                    case "price_min" -> specifications.add(new PriceMinSpecification(Double.parseDouble(value)));
                    case "price_max" -> specifications.add(new PriceMaxSpecification(Double.parseDouble(value)));
                    case "stock_min" -> specifications.add(new StockMinSpecification(Integer.parseInt(value)));
                    default -> System.out.println("Unsupported filter: " + key); // Replace with logger in production
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid filter value for " + key + ": " + value); // Replace with logger
            }
        });

        Query query = buildDynamicQuery(specifications, logic);
        long total = mongoTemplate.count(query, Product.class);
        query.with(pageable);
        List<Product> content = mongoTemplate.find(query, Product.class);
        return PageableExecutionUtils.getPage(content, pageable, () -> total);
    }

    private Query buildDynamicQuery(List<ProductSpecification> specifications, String logic) {
        Query query = new Query();
        if (!specifications.isEmpty()) {
            Criteria[] criteria = specifications.stream()
                    .map(ProductSpecification::toCriteria)
                    .toArray(Criteria[]::new);

            if ("or".equalsIgnoreCase(logic)) {
                query.addCriteria(new Criteria().orOperator(criteria));
            } else {
                query.addCriteria(new Criteria().andOperator(criteria));
            }
        }
        return query;
    }

    default void updateVariantStock(UUID variantId, int quantity, MongoTemplate mongoTemplate) {
        Query query = new Query(Criteria.where("variants.id").is(variantId));
        Product product = mongoTemplate.findOne(query, Product.class);
        if (product == null) {
            throw new ProductNotFoundException("Product variant");
        }

        Product.ProductVariant variant = product.getVariants().stream()
                .filter(v -> v.getId().equals(variantId))
                .findFirst()
                .orElseThrow(() -> new ProductNotFoundException("Product variant"));

        // Trước tiên giải phóng reserve nếu có (giả sử purchase dùng reserve)
        int reservedToDeduct = Math.min(quantity, variant.getReserveStock() != null ? variant.getReserveStock() : 0);
        int actualStockDeduct = quantity - reservedToDeduct;
        int newStock = (variant.getStock() != null ? variant.getStock() : 0) - actualStockDeduct;
        int newReserved = (variant.getReserveStock() != null ? variant.getReserveStock() : 0) - reservedToDeduct;

        if (newStock < 0 || newReserved < 0) {
            throw new ProductValidationException("Insufficient stock or reserved quantity");
        }

        Update update = new Update()
                .set("variants.$.stock", newStock)
                .set("variants.$.reserveStock", newReserved)
                .set("variants.$.updatedAt", Instant.now())
                .set("updatedAt", Instant.now());
        mongoTemplate.updateFirst(
                new Query(Criteria.where("variants.id").is(variantId)),
                update,
                Product.class
        );
    }

    default void reserveVariantStock(UUID variantId, int quantity, MongoTemplate mongoTemplate) {
        Query query = new Query(Criteria.where("variants.id").is(variantId));
        Product product = mongoTemplate.findOne(query, Product.class);
        if (product == null) {
            throw new ProductNotFoundException("Product variant");
        }

        Product.ProductVariant variant = product.getVariants().stream()
                .filter(v -> v.getId().equals(variantId))
                .findFirst()
                .orElseThrow(() -> new ProductNotFoundException("Product variant"));

        if (variant.getAvailableStock() < quantity) {
            throw new ProductValidationException("Insufficient available stock");
        }

        int newReserved = (variant.getReserveStock() != null ? variant.getReserveStock() : 0) + quantity;

        Update update = new Update()
                .set("variants.$.reserveStock", newReserved)
                .set("variants.$.updatedAt", Instant.now())
                .set("updatedAt", Instant.now());
        mongoTemplate.updateFirst(
                new Query(Criteria.where("variants.id").is(variantId)),
                update,
                Product.class
        );
    }

    default void unreserveVariantStock(UUID variantId, int quantity, MongoTemplate mongoTemplate) {
        Query query = new Query(Criteria.where("variants.id").is(variantId));
        Product product = mongoTemplate.findOne(query, Product.class);
        if (product == null) {
            throw new ProductNotFoundException("Product variant");
        }

        Product.ProductVariant variant = product.getVariants().stream()
                .filter(v -> v.getId().equals(variantId))
                .findFirst()
                .orElseThrow(() -> new ProductNotFoundException("Product variant"));

        int newReserved = (variant.getReserveStock() != null ? variant.getReserveStock() : 0) - quantity;
        if (newReserved < 0) {
            throw new ProductValidationException("Cannot unreserve more than reserved");
        }

        Update update = new Update()
                .set("variants.$.reserveStock", newReserved)
                .set("variants.$.updatedAt", Instant.now())
                .set("updatedAt", Instant.now());
        mongoTemplate.updateFirst(
                new Query(Criteria.where("variants.id").is(variantId)),
                update,
                Product.class
        );
    }
}