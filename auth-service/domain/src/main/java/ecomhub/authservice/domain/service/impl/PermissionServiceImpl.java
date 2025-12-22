package ecomhub.authservice.domain.service.impl;

import ecomhub.authservice.domain.entity.Permission;
import ecomhub.authservice.domain.service.abstracts.PermissionService;

public class PermissionServiceImpl implements PermissionService {
    @Override
    public void updateName(Permission targetPermission, String newName) {
        targetPermission.updateName(newName);
    }

    @Override
    public void updateDescription(Permission targetPermission, String newDescription) {
        targetPermission.updateDescription(newDescription);
    }

}
