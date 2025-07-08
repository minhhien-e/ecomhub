package ecomhub.authservice.adapter.input.facade;

import ecomhub.authservice.adapter.input.request.AddPermissionRequest;
import ecomhub.authservice.adapter.input.mapper.PermissionRequestMapper;
import ecomhub.authservice.application.usecase.permission.addpermission.AddPermissionHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PermissionFacade {
    private final AddPermissionHandler addPermissionHandler;
    private final PermissionRequestMapper permissionMapper;

    public void addPermission(AddPermissionRequest request) {
        var permissionCommand = permissionMapper.toCommand(request);
        addPermissionHandler.execute(permissionCommand);
    }


}
