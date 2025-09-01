package ecomhub.authservice.domain.service.abstracts;

import ecomhub.authservice.domain.entity.Account;
import ecomhub.authservice.domain.entity.Permission;
import ecomhub.authservice.domain.entity.Role;

public interface RoleService {

    void updateName(Role targetRole, Account requester, String newName);

    void updateDescription(Role targetRole, Account requester, String newDescription);

    void updateLevel(Role targetRole, Account requester, Integer newLevel);

    void updateType(Role targetRole, Account requester, String newStatus);

    void grantPermission(Role targetRole, Permission targetPermission, Account requester);

    void revokePermission(Role targetRole, Permission targetPermission, Account requester);

    void delete(Role targetRole, Account requester, boolean isHard);
}
