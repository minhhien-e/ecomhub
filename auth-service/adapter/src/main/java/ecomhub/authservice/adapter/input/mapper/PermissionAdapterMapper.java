package ecomhub.authservice.adapter.input.mapper;

import ecomhub.authservice.adapter.input.request.permisison.AddPermissionRequest;
import ecomhub.authservice.application.command.permission.add.AddPermissionCommand;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PermissionAdapterMapper {
    AddPermissionCommand toCommand(AddPermissionRequest request);
}
