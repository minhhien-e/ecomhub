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
@Document(collection = "ProductImages")
public class ProductImages {
    @Id
    private UUID id;
    private UUID productId;
    private String uri;
    @CreatedDate
    private Instant createdAt;
}