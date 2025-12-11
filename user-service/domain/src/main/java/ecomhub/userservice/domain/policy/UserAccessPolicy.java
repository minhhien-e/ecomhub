package ecomhub.userservice.domain.policy;

import ecomhub.userservice.domain.aggregate.User;

import java.util.UUID;

public interface UserAccessPolicy {
    void checkCanAccess(User user, UUID requesterId);
}
