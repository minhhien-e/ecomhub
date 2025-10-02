package ecomhub.productservice.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
//LocalDateTime
@Setter
@Getter
@Document(collection = "Product")
public class Product {
    @Id
    private UUID id;
    private String name;
    private String description;
    private UUID categoryId;
    private Double price;
    private String status;
    private List<ProductVariant> variants; // Sub-collection
    @CreatedDate
    private Instant createdAt;
    @LastModifiedDate
    private Instant updatedAt;

    @Setter
    @Getter
    public static class ProductVariant {
        private UUID id;
        @Indexed
        private UUID categoryId;
        private String sku;
        private Double price;
        private Integer stock;        // Tổng tồn kho (quantity trong Inventory)
        private Integer reserveStock; // Tồn kho đã giữ chỗ (mặc định 0)
        private String variantStatus;
        @CreatedDate
        private Instant createdAt;
        @LastModifiedDate
        private Instant updatedAt;

        public int getAvailableStock() {
            return (stock != null ? stock : 0) - (reserveStock != null ? reserveStock : 0);
        }
    }
}