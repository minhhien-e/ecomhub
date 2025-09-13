package ecomhub.productservice.repository.specification;

import org.springframework.data.mongodb.core.query.Criteria;

public class PriceMinSpecification implements ProductSpecification {
    private final double priceMin;

    public PriceMinSpecification(double priceMin) {
        this.priceMin = priceMin;
    }

    @Override
    public Criteria toCriteria() {
        return Criteria.where("price").gte(priceMin);
    }
}