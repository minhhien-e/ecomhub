package ecomhub.authservice.application.mapper;

import ecomhub.authservice.application.command.AddRoleCommand;
import ecomhub.authservice.domain.model.Role;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    Role toDomain(AddRoleCommand command);
}
