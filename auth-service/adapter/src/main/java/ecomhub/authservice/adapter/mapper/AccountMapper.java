package ecomhub.authservice.adapter.mapper;

import ecomhub.authservice.adapter.dto.request.RegisterBasicRequest;
import ecomhub.authservice.application.command.RegisterAccountCommand;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccountMapper {
    RegisterAccountCommand toCommand(RegisterBasicRequest request);
}
