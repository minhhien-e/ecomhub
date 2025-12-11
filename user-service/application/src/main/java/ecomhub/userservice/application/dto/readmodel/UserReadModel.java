package ecomhub.userservice.application.dto.readmodel;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public record UserReadModel(UUID id,
                            String fullName,
                            String email,
                            String phoneNumber,
                            String gender,
                            LocalDate birthDate,
                            String avatarUrl,
                            LocalDateTime createdAt)  {
}
