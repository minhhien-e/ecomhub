package ecomhub.productservice.repository;

import ecomhub.productservice.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;
import java.util.UUID;

public interface ProductRepositoryCustom {
    Page<Product> findByDynamicFilters(Map<String, String> filters, String logic, Pageable pageable);

    void updateVariantStock(UUID variantId, int quantity);

    void reserveVariantStock(UUID variantId, int quantity);

    void unReserveVariantStock(UUID variantId, int quantity);
}
