package ecomhub.authservice.application.mapper;

import ecomhub.authservice.application.usecase.account.regiseraccount.RegisterAccountCommand;
import ecomhub.authservice.domain.entities.Account;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccountCommandMapper {
    Account toDomain(RegisterAccountCommand command);
}
