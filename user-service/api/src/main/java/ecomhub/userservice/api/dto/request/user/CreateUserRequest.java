package ecomhub.userservice.api.dto.request.user;

public record CreateUserRequest(String fullName, String email, String phoneNumber) {
}
