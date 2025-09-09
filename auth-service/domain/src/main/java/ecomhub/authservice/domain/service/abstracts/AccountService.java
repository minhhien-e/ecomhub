package ecomhub.authservice.domain.service.abstracts;

import ecomhub.authservice.domain.entity.Account;
import ecomhub.authservice.domain.entity.Role;

public interface AccountService {
    void assignRole(Account targetAccount, Role targetRole, Account requester);

    void revokeRole(Account targetAccount, Role targetRole, Account requester);

    Account register(String username, String email, String phoneNumber, String password, String passwordConfirm, String provider, Role role);
}
