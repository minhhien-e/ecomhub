package ecomhub.productservice.repository.specification;

import org.springframework.data.mongodb.core.query.Criteria;

public class NameSpecification implements ProductSpecification {
    private final String name;

    public NameSpecification(String name) {
        this.name = name;
    }

    @Override
    public Criteria toCriteria() {
        return Criteria.where("name").regex(name, "i");
    }
}