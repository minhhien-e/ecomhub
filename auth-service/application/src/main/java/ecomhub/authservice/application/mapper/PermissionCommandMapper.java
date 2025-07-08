package ecomhub.authservice.application.mapper;

import ecomhub.authservice.application.usecase.permission.addpermission.AddPermissionCommand;
import ecomhub.authservice.domain.entities.Permission;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PermissionCommandMapper {
    Permission toDomain(AddPermissionCommand command);
}
