package ecomhub.authservice.infrastructure.inbound.web.mapper;

import ecomhub.authservice.application.command.permission.add.AddPermissionCommand;
import ecomhub.authservice.infrastructure.inbound.web.dto.request.permisison.AddPermissionRequest;

public class PermissionRequestMapper {
    public static AddPermissionCommand toCommand(AddPermissionRequest request) {
        return new AddPermissionCommand(request.name(), request.description(), request.key());
    }

}
