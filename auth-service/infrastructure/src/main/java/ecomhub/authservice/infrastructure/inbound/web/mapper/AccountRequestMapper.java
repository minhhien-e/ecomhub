package ecomhub.authservice.infrastructure.inbound.web.mapper;

import ecomhub.authservice.application.command.account.register.RegisterAccountCommand;
import ecomhub.authservice.application.command.account.role.grant.GrantRoleCommand;
import ecomhub.authservice.application.command.account.role.revoke.RevokeRoleCommand;
import ecomhub.authservice.common.dto.request.account.GrantRoleRequest;
import ecomhub.authservice.common.dto.request.account.RevokeRoleRequest;
import ecomhub.authservice.common.enums.ProviderType;
import ecomhub.authservice.common.utils.StringUtils;
import ecomhub.authservice.common.dto.request.account.RegisterBasicRequest;

import java.util.UUID;

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

    public static GrantRoleCommand toCommand(GrantRoleRequest request, UUID requesterId) {
        return new GrantRoleCommand(requesterId, request.roleId(), request.accountId());
    }

    public static RevokeRoleCommand toCommand(RevokeRoleRequest request, UUID requesterId) {
        return new RevokeRoleCommand(requesterId, request.roleId(), request.accountId());
    }

}
