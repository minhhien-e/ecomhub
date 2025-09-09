package ecomhub.authservice.domain.service.impl;

import ecomhub.authservice.common.exception.abstracts.ForbiddenException;
import ecomhub.authservice.common.exception.concrete.account.PasswordNotMatchException;
import ecomhub.authservice.domain.entity.Account;
import ecomhub.authservice.domain.entity.Role;
import ecomhub.authservice.domain.service.abstracts.AccountService;
import ecomhub.authservice.domain.service.abstracts.PasswordHashService;

public class AccountServiceImpl implements AccountService {
    private final PasswordHashService passwordHashService;

    public AccountServiceImpl(PasswordHashService passwordHashService) {
        this.passwordHashService = passwordHashService;
    }

    @Override
    public void assignRole(Account targetAccount, Role targetRole, Account requester) {
        var highestRole = targetAccount.getHighestRole();
        if (requester.getRoles().stream().anyMatch(role ->
                role.greaterThan(targetRole) && role.hasPermission("account.role.assign") && role.greaterThan(highestRole)))
            targetAccount.assignRole(targetRole);
        else throw new ForbiddenException("assign a role to the account");
    }

    @Override
    public void revokeRole(Account targetAccount, Role targetRole, Account requester) {
        var highestRole = targetAccount.getHighestRole();
        if (requester.getRoles().stream().anyMatch(role ->
                role.greaterThan(targetRole) && role.hasPermission("account.role.revoke") && role.greaterThan(highestRole)))
            targetAccount.revokeRole(targetRole);
        else throw new ForbiddenException("revoke a role to the account.");
    }

    @Override
    public Account register(String username, String email, String phoneNumber, String password, String passwordConfirm, String provider, Role role) {
        if (!password.equals(passwordConfirm)) {
            throw new PasswordNotMatchException();
        }
        var account = new Account(email, username, phoneNumber, password, provider);
        account.hashPassword(passwordHashService.hash(account.getRawPassword()));
        account.assignRole(role);
        return account;
    }

}
