package ecomhub.authservice.infrastructure.inbound.web.mapper;

import ecomhub.authservice.application.command.permission.add.AddPermissionCommand;
import ecomhub.authservice.application.command.permission.delete.DeletePermissionCommand;
import ecomhub.authservice.application.query.permission.getall.GetAllPermissionQuery;
import ecomhub.authservice.application.command.permission.update.description.UpdateDescriptionPermissionCommand;
import ecomhub.authservice.application.command.permission.update.name.UpdateNamePermissionCommand;
import ecomhub.authservice.common.dto.request.permisison.*;

import java.util.UUID;

public class PermissionRequestMapper {
    //region Add
    public static AddPermissionCommand toCommand(AddPermissionRequest request) {
        return new AddPermissionCommand(request.name(), request.description(), request.key());
    }

    //endregion
    //region Update Description
    public static UpdateDescriptionPermissionCommand toCommand(UpdateDescriptionPermissionRequest request,
                                                               UUID permissionId,
                                                               UUID requesterId) {
        return new UpdateDescriptionPermissionCommand(request.newDescription(), permissionId, requesterId);
    }

    //endregion
    //region Delete
    public static DeletePermissionCommand toCommand(DeletePermissionRequest request, UUID requesterId) {
        return new DeletePermissionCommand(request.permissionId(), requesterId);
    }

    //endregion
    //region Update Name
    public static UpdateNamePermissionCommand toCommand(UpdateNamePermissionRequest request, UUID permissionId, UUID requesterId) {
        return new UpdateNamePermissionCommand(request.newName(), permissionId, requesterId);
    }

    //endregion
    //region Get All
    public static GetAllPermissionQuery toQuery(GetAllPermissionRequest request) {
        return new GetAllPermissionQuery();
    }
    //endregion
}
