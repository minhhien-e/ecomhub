package ecomhub.authservice.adapter.input.mapper;

import ecomhub.authservice.adapter.input.request.AddPermissionRequest;
import ecomhub.authservice.application.usecase.permission.addpermission.AddPermissionCommand;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PermissionRequestMapper {
    AddPermissionCommand toCommand(AddPermissionRequest request);
}
