package ecomhub.authservice.infrastructure.inbound.web.mapper;

import ecomhub.authservice.application.command.role.add.AddRoleCommand;
import ecomhub.authservice.application.command.role.delete.DeleteRoleCommand;
import ecomhub.authservice.application.command.role.permission.grant.GrantPermissionCommand;
import ecomhub.authservice.application.command.role.permission.revoke.RevokePermissionCommand;
import ecomhub.authservice.application.query.role.getall.GetAllRoleQuery;
import ecomhub.authservice.application.command.role.update.description.UpdateDescriptionRoleCommand;
import ecomhub.authservice.application.command.role.update.level.UpdateLevelRoleCommand;
import ecomhub.authservice.application.command.role.update.name.UpdateNameRoleCommand;
import ecomhub.authservice.common.dto.request.role.*;

import java.util.UUID;

public class RoleRequestMapper {
    public static AddRoleCommand toCommand(AddRoleRequest request, UUID requesterId) {
        return AddRoleCommand.builder()
                .requesterId(requesterId)
                .name(request.name())
                .key(request.key())
                .type(request.type())
                .level(request.level())
                .description(request.description())
                .permissionKeys(request.permissionKeys())
                .build();
    }

    public static UpdateDescriptionRoleCommand toCommand(UpdateDescriptionRoleRequest request, UUID requesterId) {
        return new UpdateDescriptionRoleCommand(request.newDescription(), request.roleId(), requesterId);
    }

    public static UpdateNameRoleCommand toCommand(UpdateNameRoleRequest request, UUID requesterId) {
        return new UpdateNameRoleCommand(request.newName(), request.roleId(), requesterId);
    }


    public static UpdateLevelRoleCommand toCommand(UpdateLevelRoleRequest request,  UUID requesterId) {
        return new UpdateLevelRoleCommand(request.newLevel(), request.roleId(), requesterId);
    }

    public static DeleteRoleCommand toCommand(DeleteRoleRequest request, UUID requesterId) {
        return new DeleteRoleCommand(request.roleId(), requesterId);
    }

    public static GetAllRoleQuery toQuery(GetAllRoleRequest request) {
        return new GetAllRoleQuery();
    }

    public static GrantPermissionCommand toCommand(GrantPermissionRequest request, UUID roleId, UUID requesterId) {
        return new GrantPermissionCommand(requesterId, roleId, request.permissionKeys());
    }

    public static RevokePermissionCommand toCommand(RevokePermissionRequest request, UUID roleId, UUID requesterId) {
        return new RevokePermissionCommand(requesterId, roleId, request.permissionId());
    }

}
