package ecomhub.authservice.application.command.account.register;

import ecomhub.authservice.application.command.interfaces.ICommand;
import ecomhub.authservice.application.enums.RoleName;
import ecomhub.authservice.common.enums.Provider;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterAccountCommand implements ICommand {
    private @NotBlank String username;
    private @NotBlank String password;
    private @Email String email;
    @NotBlank
    @Pattern(regexp = "^[0-9]{8}$", message = "Số điện thoại không hợp lệ")
    private String phoneNumber;
    private @NotBlank Provider provider;
    private @NotNull List<RoleName> roles;
}

