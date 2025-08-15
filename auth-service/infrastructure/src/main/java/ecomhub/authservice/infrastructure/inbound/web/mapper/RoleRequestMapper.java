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
    //region add
    public static AddRoleCommand toCommand(AddRoleRequest request) {
        return new AddRoleCommand(request.name(), request.description(), request.level(), request.permissionKeys());
    }

    //endregion
    //region update
    public static UpdateDescriptionRoleCommand toCommand(UpdateDescriptionRoleRequest request, UUID roleId, UUID requesterId) {
        return new UpdateDescriptionRoleCommand(request.newDescription(), roleId, requesterId);
    }

    public static UpdateNameRoleCommand toCommand(UpdateNameRoleRequest request, UUID roleId, UUID requesterId) {
        return new UpdateNameRoleCommand(request.newName(), roleId, requesterId);
    }


    public static UpdateLevelRoleCommand toCommand(UpdateLevelRoleRequest request, UUID roleId, UUID requesterId) {
        return new UpdateLevelRoleCommand(request.newLevel(), roleId, requesterId);
    }

    //endregion
    //region delete
    public static DeleteRoleCommand toCommand(DeleteRoleRequest request, UUID requesterId) {
        return new DeleteRoleCommand(request.roleId(), requesterId);
    }

    //endregion
    //region get all
    public static GetAllRoleQuery toQuery(GetAllRoleRequest request) {
        return new GetAllRoleQuery();
    }

    //endregion
    //region permission management
    public static GrantPermissionCommand toCommand(GrantPermissionRequest request, UUID roleId, UUID requesterId) {
        return new GrantPermissionCommand(requesterId, roleId, request.permissionKeys());
    }

    public static RevokePermissionCommand toCommand(RevokePermissionRequest request, UUID roleId, UUID requesterId) {
        return new RevokePermissionCommand(requesterId, roleId, request.permissionKey());
    }
    //endregion

}
