package ecomhub.authservice.domain.service.abstracts;

import ecomhub.authservice.domain.entity.Permission;

public interface PermissionService {
    void updateName(Permission targetPermission, String newName);

    void updateDescription(Permission targetPermission, String newDescription);
}
