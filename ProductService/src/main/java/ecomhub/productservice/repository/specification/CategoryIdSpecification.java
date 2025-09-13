package ecomhub.productservice.repository.specification;

import org.springframework.data.mongodb.core.query.Criteria;

import java.util.UUID;

public class CategoryIdSpecification implements ProductSpecification {
    private final UUID categoryId;

    public CategoryIdSpecification(UUID categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public Criteria toCriteria() {
        return Criteria.where("categoryId").is(categoryId);
    }
}