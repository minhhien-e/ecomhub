package ecomhub.authservice.infrastructure.inbound.web.mapper;

import ecomhub.authservice.application.command.account.register.RegisterAccountCommand;
import ecomhub.authservice.application.command.account.role.grant.GrantRoleCommand;
import ecomhub.authservice.application.command.account.role.revoke.RevokeRoleCommand;
import ecomhub.authservice.common.dto.request.account.GrantRoleRequest;
import ecomhub.authservice.common.dto.request.account.RegisterBasicRequest;
import ecomhub.authservice.common.dto.request.account.RevokeRoleRequest;

import java.util.UUID;

public class AccountRequestMapper {
    public static RegisterAccountCommand toCommand(RegisterBasicRequest request) {
        return new RegisterAccountCommand(request.username(),
                request.password(),
                request.email(),
                request.phoneNumber(),
                request.provider());
    }

    public static GrantRoleCommand toCommand(GrantRoleRequest request, UUID requesterId) {
        return new GrantRoleCommand(requesterId, request.roleId(), request.accountId());
    }

    public static RevokeRoleCommand toCommand(RevokeRoleRequest request, UUID requesterId) {
        return new RevokeRoleCommand(requesterId, request.roleId(), request.accountId());
    }

}
