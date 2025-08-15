package ecomhub.authservice.domain.service.impl;

import ecomhub.authservice.domain.entity.Account;
import ecomhub.authservice.domain.entity.Role;
import ecomhub.authservice.domain.service.abstracts.AccountService;

public class AccountServiceImpl implements AccountService {
    @Override
    public boolean canBeGrantedRoleBy(Account targetAccount, Role targetRole, Account requester) {
        var highestRole = targetAccount.getHighestRole();
        return canPerformAction(targetRole, highestRole, requester, "account.role.grant");
    }

    @Override
    public boolean canBeRevokedRoleBy(Account targetAccount, Role targetRole, Account requester) {
        var highestRole = targetAccount.getHighestRole();
        return canPerformAction(targetRole, highestRole, requester, "account.role.revoke");
    }

    //Kiểm tra có thực thi một quyền cụ thể
    private boolean canPerformAction(Role targetRole, Role targetHighestRole, Account requester, String permissionKey) {
        return requester.getRoles().stream().anyMatch(role ->
                role.greaterThan(targetRole) && role.hasPermission(permissionKey) && role.greaterThan(targetHighestRole)
        );
    }
}
