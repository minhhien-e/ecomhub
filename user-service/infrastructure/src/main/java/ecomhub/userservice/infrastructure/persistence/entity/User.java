package ecomhub.userservice.infrastructure.persistence.entity;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Document(collection = "users")
@Data
@Builder
public class User {
    @Id
    private UUID id;

    @Email
    @NotBlank
    @Size(max = 50)
    private String email;

    @NotBlank
    @Size(max = 10)
    private String phoneNumber;

    @NotBlank
    @Size(max = 255)
    private String fullName;

    private Gender gender;

    private Date birthDate;

    @Size(max = 2000)
    private String avatarUrl;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    // Enum cho gender
    public enum Gender {
        MALE, FEMALE, OTHER
    }
}

