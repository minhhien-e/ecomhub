package ecomhub.authservice.application.mapper;

import ecomhub.authservice.application.command.AddPermissionCommand;
import ecomhub.authservice.domain.model.Permission;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permission toDomain(AddPermissionCommand command);
}
