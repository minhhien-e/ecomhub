package ecomhub.productservice.repository.specification;

import org.springframework.data.mongodb.core.query.Criteria;

public interface ProductSpecification {
    Criteria toCriteria();
}