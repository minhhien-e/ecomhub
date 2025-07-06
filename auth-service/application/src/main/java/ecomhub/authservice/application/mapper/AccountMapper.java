package ecomhub.authservice.application.mapper;

import ecomhub.authservice.application.command.RegisterAccountCommand;
import ecomhub.authservice.domain.model.Account;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccountMapper {
    Account toDomain(RegisterAccountCommand command);
}
