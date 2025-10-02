package ecomhub.productservice.repository;

import ecomhub.productservice.exception.ProductNotFoundException;
import ecomhub.productservice.exception.ProductValidationException;
import ecomhub.productservice.model.Product;
import ecomhub.productservice.repository.specification.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class ProductRepositoryCustomImpl implements ProductRepositoryCustom {
    private final MongoTemplate mongoTemplate;

    @Override
    public Page<Product> findByDynamicFilters(Map<String, String> filters, String logic, Pageable pageable) {
        List<ProductSpecification> specifications = new ArrayList<>();

        filters.forEach((key, value) -> {
            try {
                switch (key.toLowerCase()) {
                    case "name" -> specifications.add(new NameSpecification(value));
                    case "categoryid" -> specifications.add(new CategoryIdSpecification(UUID.fromString(value)));
                    case "price_min" -> specifications.add(new PriceMinSpecification(Double.parseDouble(value)));
                    case "price_max" -> specifications.add(new PriceMaxSpecification(Double.parseDouble(value)));
                    case "stock_min" -> specifications.add(new StockMinSpecification(Integer.parseInt(value)));
                    default -> log.warn("Unsupported filter: {}", key);
                }
            } catch (IllegalArgumentException e) {
                log.error("Invalid filter value for {}: {}", key, value, e);
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

    @Override
    public void updateVariantStock(UUID variantId, int quantity) {
        Product.ProductVariant variant = getVariantOrThrow(variantId);

        // Giải phóng reserve trước nếu có
        int reservedToDeduct = Math.min(quantity, safeValue(variant.getReserveStock()));
        int actualStockDeduct = quantity - reservedToDeduct;
        int newStock = safeValue(variant.getStock()) - actualStockDeduct;
        int newReserved = safeValue(variant.getReserveStock()) - reservedToDeduct;

        if (newStock < 0 || newReserved < 0) {
            throw new ProductValidationException("Insufficient stock or reserved quantity");
        }

        Update update = baseUpdate()
                .set("variants.$.stock", newStock)
                .set("variants.$.reserveStock", newReserved);

        applyUpdate(variantId, update);
        log.info("Updated stock for variant ID: {}, new stock: {}, new reserved: {}", variantId, newStock, newReserved);
    }

    @Override
    public void reserveVariantStock(UUID variantId, int quantity) {
        Product.ProductVariant variant = getVariantOrThrow(variantId);

        if (variant.getAvailableStock() < quantity) {
            throw new ProductValidationException("Insufficient available stock");
        }

        int newReserved = safeValue(variant.getReserveStock()) + quantity;

        Update update = baseUpdate()
                .set("variants.$.reserveStock", newReserved);

        applyUpdate(variantId, update);
        log.info("Reserved stock for variant ID: {}, new reserved: {}", variantId, newReserved);
    }

    @Override
    public void unReserveVariantStock(UUID variantId, int quantity) {
        Product.ProductVariant variant = getVariantOrThrow(variantId);

        int newReserved = safeValue(variant.getReserveStock()) - quantity;
        if (newReserved < 0) {
            throw new ProductValidationException("Cannot unreserve more than reserved");
        }

        Update update = baseUpdate()
                .set("variants.$.reserveStock", newReserved);

        applyUpdate(variantId, update);
        log.info("Unreserved stock for variant ID: {}, new reserved: {}", variantId, newReserved);
    }

    private Product.ProductVariant getVariantOrThrow(UUID variantId) {
        Query query = new Query(Criteria.where("variants.id").is(variantId));
        Product product = mongoTemplate.findOne(query, Product.class);
        if (product == null) {
            throw new ProductNotFoundException("Product variant");
        }

        return product.getVariants().stream()
                .filter(v -> v.getId().equals(variantId))
                .findFirst()
                .orElseThrow(() -> new ProductNotFoundException("Product variant"));
    }

    private Update baseUpdate() {
        return new Update()
                .set("variants.$.updatedAt", Instant.now())
                .set("updatedAt", Instant.now());
    }

    private void applyUpdate(UUID variantId, Update update) {
        mongoTemplate.updateFirst(
                new Query(Criteria.where("variants.id").is(variantId)),
                update,
                Product.class
        );
    }

    private int safeValue(Integer value) {
        return value != null ? value : 0;
    }
}