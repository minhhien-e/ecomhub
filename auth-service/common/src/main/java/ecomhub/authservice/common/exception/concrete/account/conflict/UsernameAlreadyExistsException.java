package ecomhub.authservice.common.exception.concrete.account.conflict;

import ecomhub.authservice.common.exception.conflict.ResourceAlreadyExistsException;

public class UsernameAlreadyExistsException extends ResourceAlreadyExistsException {
    public UsernameAlreadyExistsException(String username) {
        super("Tên người dùng " + username);
    }
}
