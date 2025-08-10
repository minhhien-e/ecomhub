package ecomhub.authservice.adapter.input.mapper;

import ecomhub.authservice.adapter.input.request.account.RegisterBasicRequest;
import ecomhub.authservice.application.command.account.register.RegisterAccountCommand;
import ecomhub.authservice.common.enums.ProviderType;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface AccountAdapterMapper {
    RegisterAccountCommand toCommand(RegisterBasicRequest request);

    @AfterMapping
    default void setDefaultProvider(@MappingTarget RegisterAccountCommand command) {
        if (command.getProvider() == null || command.getProvider().isBlank()) {
            command.setProvider(ProviderType.LOCAL.name());
        }
    }
}
