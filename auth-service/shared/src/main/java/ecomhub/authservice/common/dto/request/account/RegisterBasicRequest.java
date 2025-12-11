package ecomhub.authservice.common.dto.request.account;

public record RegisterBasicRequest
        (String fullName,
         String username,
         String password,
         String passwordConfirm,
         String email,
         String phoneNumber,
         String provider) {
}
