package ecomhub.authservice.application.mapper;

import ecomhub.authservice.application.command.account.register.RegisterAccountCommand;
import ecomhub.authservice.domain.entity.Account;
import ecomhub.authservice.domain.service.PasswordHashService;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccountCommandMapper {
    default Account toDomain(RegisterAccountCommand command, PasswordHashService passwordHashService) {
        return new Account(
                command.getEmail(),
                command.getUsername(),
                command.getPhoneNumber(),
                command.getPassword(),
                command.getProvider(),
                passwordHashService
        );
    }
}
