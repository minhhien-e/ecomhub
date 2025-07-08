package ecomhub.authservice.adapter.input.facade;

import ecomhub.authservice.adapter.input.request.AddRoleRequest;
import ecomhub.authservice.adapter.input.mapper.RoleRequestMapper;
import ecomhub.authservice.application.usecase.role.addrole.AddRoleHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleFacade {
    private final AddRoleHandler addRoleHandler;
    private final RoleRequestMapper roleMapper;

    public void addRole(AddRoleRequest request) {
        var addRoleCommand = roleMapper.toCommand(request);
        addRoleHandler.execute(addRoleCommand);
    }
}
