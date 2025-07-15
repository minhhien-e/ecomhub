package ecomhub.authservice.common.exception.concrete.account.notfound;

import ecomhub.authservice.common.exception.notfound.ResourceNotFoundException;

public class AccountNotFoundException extends ResourceNotFoundException {
    public AccountNotFoundException(String identifier) {
        super("Tài khoản với thông tin: " + identifier);
    }
}
