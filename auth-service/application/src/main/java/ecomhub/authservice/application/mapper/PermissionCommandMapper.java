package ecomhub.authservice.application.mapper;

import ecomhub.authservice.application.command.permission.add.AddPermissionCommand;
import ecomhub.authservice.domain.entity.Permission;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PermissionCommandMapper {
    default Permission toDomain(AddPermissionCommand command) {
        return new Permission(command.getName(), command.getKey(), command.getDescription());
    }

}
