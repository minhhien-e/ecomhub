package ecomhub.authservice.common.dto.request.account;

public record RegisterBasicRequest
        (String username,
         String password,
         String email,
         String phoneNumber,
         String provider) {
}
