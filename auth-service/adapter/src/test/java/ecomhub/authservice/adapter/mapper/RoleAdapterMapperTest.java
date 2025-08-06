package ecomhub.authservice.adapter.mapper;

import ecomhub.authservice.adapter.input.mapper.RoleAdapterMapper;
import ecomhub.authservice.adapter.input.request.role.AddRoleRequest;
import ecomhub.authservice.adapter.input.request.role.DeleteRoleRequest;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.Collections;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RoleAdapterMapperTest {
    private final RoleAdapterMapper mapper = Mappers.getMapper(RoleAdapterMapper.class);

    @Test
    void toDeleteCommand_WhenValidInput_ShouldReturnCommand() {
        DeleteRoleRequest request = new DeleteRoleRequest(UUID.randomUUID());
        var input = mapper.toCommand(request);
        assertEquals(request.roleId(), input.getRoleId());
    }
    @Test
    void toAddCommand_WhenValidInput_ShouldReturnCommand() {
        AddRoleRequest request = new AddRoleRequest("name", "description", Collections.emptySet());
        var input = mapper.toCommand(request);
        assertEquals(request.name(), input.getName());
        assertEquals(request.description(),input.getDescription());
        assertEquals(request.permissionKeys(),input.getPermissionKeys());
    }
}
