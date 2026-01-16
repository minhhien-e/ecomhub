package ecomhub.salerservice.model;

import ecomhub.salerservice.enums.Status;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.File;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "Shops")
public class Shop {
    @Id
    @Builder.Default
    private UUID id = UUID.randomUUID();
    private UUID sellerId;
    private String name;
    private String bannerUrl;
    @Email
    private String email;
    private String phone;
    private String address;

    @Builder.Default
    private Status status = Status.PENDING;

    private String contact;
    @CreationTimestamp
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;
}
