package ecomhub.authservice.common.exception.concrete.account;

import ecomhub.authservice.common.exception.abstracts.ResourceAlreadyExistsException;

public class UsernameAlreadyExistsException extends ResourceAlreadyExistsException {
    public UsernameAlreadyExistsException(String username) {
        super("Tên người dùng " + username);
    }
}
