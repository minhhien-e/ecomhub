package ecomhub.authservice.adapter.input.mapper;

import ecomhub.authservice.adapter.input.request.RegisterBasicRequest;
import ecomhub.authservice.application.command.account.register.RegisterAccountCommand;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccountAdapterMapper {
    RegisterAccountCommand toCommand(RegisterBasicRequest request);
}
