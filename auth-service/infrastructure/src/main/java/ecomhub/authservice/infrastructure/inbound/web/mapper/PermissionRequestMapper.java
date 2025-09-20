package ecomhub.authservice.infrastructure.inbound.web.mapper;

import ecomhub.authservice.application.command.permission.update.description.UpdateDescriptionPermissionCommand;
import ecomhub.authservice.application.command.permission.update.name.UpdateNamePermissionCommand;
import ecomhub.authservice.application.query.permission.getall.GetAllPermissionQuery;
import ecomhub.authservice.common.dto.request.permisison.GetAllPermissionRequest;
import ecomhub.authservice.common.dto.request.permisison.UpdateDescriptionPermissionRequest;
import ecomhub.authservice.common.dto.request.permisison.UpdateNamePermissionRequest;

public class PermissionRequestMapper {
    public static UpdateDescriptionPermissionCommand toCommand(UpdateDescriptionPermissionRequest request) {
        return new UpdateDescriptionPermissionCommand(request.newDescription(), request.permissionId());
    }

    public static UpdateNamePermissionCommand toCommand(UpdateNamePermissionRequest request) {
        return new UpdateNamePermissionCommand(request.newName(), request.permissionId());
    }

    public static GetAllPermissionQuery toQuery(GetAllPermissionRequest request) {
        return new GetAllPermissionQuery();
    }
}
