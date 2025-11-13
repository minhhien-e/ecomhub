package ecomhub.userservice.application.dto.readmodel;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record UserReadModel(String id,
                            String fullName,
                            String email,
                            String phoneNumber,
                            String gender,
                            LocalDate birthDate,
                            String avatarUrl,
                            LocalDateTime createdAt)  {
}
