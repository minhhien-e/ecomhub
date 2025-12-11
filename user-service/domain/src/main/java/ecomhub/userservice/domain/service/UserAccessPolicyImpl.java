package ecomhub.userservice.domain.service;

import ecomhub.userservice.domain.aggregate.User;
import ecomhub.userservice.domain.exception.user.UserAccessDeniedException;
import ecomhub.userservice.domain.policy.UserAccessPolicy;

import java.util.UUID;

public class UserAccessPolicyImpl implements UserAccessPolicy {
    @Override
    public void checkCanAccess(User user, UUID requesterId) {
        // Simple check: only the user themselves can access their data
        // In a real app, we would also check for ADMIN role
        if (!user.getId().equals(requesterId)) {
            throw new UserAccessDeniedException();
        }
    }
}
