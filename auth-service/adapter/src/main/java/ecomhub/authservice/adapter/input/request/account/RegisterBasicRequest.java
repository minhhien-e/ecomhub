package ecomhub.authservice.adapter.input.request.account;

import java.util.List;

public record RegisterBasicRequest
        (String username,
         String password,
         String email,
         String phoneNumber,
         String provider,
         List<String> roles
        ) {
}
