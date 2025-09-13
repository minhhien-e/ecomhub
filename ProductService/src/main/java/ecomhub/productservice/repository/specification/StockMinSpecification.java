package ecomhub.productservice.repository.specification;

import org.springframework.data.mongodb.core.query.Criteria;

public class StockMinSpecification implements ProductSpecification {
    private final int stockMin;

    public StockMinSpecification(int stockMin) {
        this.stockMin = stockMin;
    }

    @Override
    public Criteria toCriteria() {
        return Criteria.where("variants.stock").gte(stockMin);
    }
}