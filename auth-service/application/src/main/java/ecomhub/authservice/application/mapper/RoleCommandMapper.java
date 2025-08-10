package ecomhub.authservice.application.mapper;

import ecomhub.authservice.application.command.role.add.AddRoleCommand;
import ecomhub.authservice.domain.entity.Role;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoleCommandMapper {
    default Role toDomain(AddRoleCommand command) {
        return new Role(command.getName(), command.getDescription(),command.getLevel());
    }
}
