package ecomhub.authservice.adapter.input.request;

import ecomhub.authservice.application.enums.RoleName;
import ecomhub.authservice.common.enums.ProviderType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.util.List;

public record RegisterBasicRequest
        (@NotBlank String username,
         @NotBlank String password,
         @Email String email,
         @NotBlank @Pattern(regexp = "^[0-9]{8}$", message = "Số điện thoại không hợp lệ")
         String phoneNumber,
         @NotNull ProviderType provider,
          @NotNull List<RoleName> roles
        ) {
}
