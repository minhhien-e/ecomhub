package ecomhub.authservice.domain.service.abstracts;

import ecomhub.authservice.domain.entity.Account;

public interface PermissionService {
    boolean canBeModifiedBy(Account requester);

    boolean canBeDeletedBy(Account requester);
}
