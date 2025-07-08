package ecomhub.authservice.adapter.input.mapper;

import ecomhub.authservice.adapter.input.request.RegisterBasicRequest;
import ecomhub.authservice.application.usecase.account.regiseraccount.RegisterAccountCommand;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccountRequestMapper {
    RegisterAccountCommand toCommand(RegisterBasicRequest request);
}
