package ecomhub.authservice.infrastructure.inbound.web.mapper;

import ecomhub.authservice.application.command.account.register.RegisterAccountCommand;
import ecomhub.authservice.common.enums.ProviderType;
import ecomhub.authservice.common.utils.StringUtils;
import ecomhub.authservice.infrastructure.inbound.web.dto.request.account.RegisterBasicRequest;

public class AccountRequestMapper {
    public static RegisterAccountCommand toCommand(RegisterBasicRequest request) {
        String provider = StringUtils.isNullOrBlank(request.provider()) ? ProviderType.LOCAL.name() : request.provider();
        return new RegisterAccountCommand(request.username(),
                request.password(),
                request.email(),
                request.phoneNumber(),
                provider,
                request.roles());
    }

}
