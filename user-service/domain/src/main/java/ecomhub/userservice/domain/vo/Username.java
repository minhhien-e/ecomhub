package ecomhub.userservice.domain.vo;

import ecomhub.userservice.domain.exception.user.InvalidUsernameException;

public record Username(String value) {
    public Username {
        if (value == null || value.isBlank()) {
            throw new InvalidUsernameException();
        }
    }
}
