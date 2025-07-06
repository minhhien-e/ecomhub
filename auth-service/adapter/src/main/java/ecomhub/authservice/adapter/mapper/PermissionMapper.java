package ecomhub.authservice.adapter.mapper;

import ecomhub.authservice.adapter.dto.request.AddPermissionRequest;
import ecomhub.authservice.application.command.AddPermissionCommand;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    AddPermissionCommand toCommand(AddPermissionRequest request);
}
