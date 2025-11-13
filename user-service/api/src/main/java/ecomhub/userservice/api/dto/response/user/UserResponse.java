package ecomhub.userservice.api.dto.response.user;


import java.time.LocalDate;
import java.time.LocalDateTime;

public record UserResponse(String id,
                           String fullName,
                           String email,
                           String phoneNumber,
                           String gender,
                           LocalDate birthDate,
                           String avatarUrl,
                           LocalDateTime createdAt) {
}
