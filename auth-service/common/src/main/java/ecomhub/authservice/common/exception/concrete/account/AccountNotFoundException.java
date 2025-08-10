package ecomhub.authservice.common.exception.concrete.account;

import ecomhub.authservice.common.exception.abstracts.ResourceNotFoundException;

import java.util.UUID;

public class AccountNotFoundException extends ResourceNotFoundException {
    public AccountNotFoundException(String identifier) {
        super("Tài khoản với thông tin: " + identifier);
    }
    public AccountNotFoundException(UUID id) {
        super("Tài khoản có id: " + id);
    }
}
