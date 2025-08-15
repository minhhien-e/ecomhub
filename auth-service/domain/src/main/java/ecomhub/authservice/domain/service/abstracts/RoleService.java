package ecomhub.authservice.domain.service.abstracts;

import ecomhub.authservice.domain.entity.Account;
import ecomhub.authservice.domain.entity.Role;

public interface RoleService {
    boolean canBeModifiedBy(Role targetRole, Account requester);
    boolean canBeDeletedBy(Role targetRole, Account requester);
    boolean canBeGrantPermissionBy(Role targetRole, Account requester);
    boolean canBeRevokePermissionBy(Role targetRole, Account requester);

}
