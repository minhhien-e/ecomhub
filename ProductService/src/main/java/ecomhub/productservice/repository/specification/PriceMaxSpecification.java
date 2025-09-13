package ecomhub.productservice.repository.specification;

import org.springframework.data.mongodb.core.query.Criteria;

public class PriceMaxSpecification implements ProductSpecification {
    private final double priceMax;

    public PriceMaxSpecification(double priceMax) {
        this.priceMax = priceMax;
    }

    @Override
    public Criteria toCriteria() {
        return Criteria.where("price").lte(priceMax);
    }
}