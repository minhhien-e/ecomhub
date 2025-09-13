package ecomhub.productservice.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.UUID;

@Setter
@Getter
@Document(collection = "ProductReviews")
public class ProductReviews {
    @Id
    private UUID id;
    private UUID productId;
    private UUID userId;
    private Integer rating;
    @CreatedDate
    private Instant createdAt;
}