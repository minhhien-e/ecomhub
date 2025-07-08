package ecomhub.authservice.application.mapper;

import ecomhub.authservice.application.usecase.role.addrole.AddRoleCommand;
import ecomhub.authservice.domain.entities.Role;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoleCommandMapper {
    Role toDomain(AddRoleCommand command);
}
