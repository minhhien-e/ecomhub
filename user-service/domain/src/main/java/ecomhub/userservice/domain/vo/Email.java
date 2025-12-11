package ecomhub.userservice.domain.vo;

import ecomhub.userservice.domain.exception.user.InvalidEmailException;

public record Email(String value) {
    public Email {
        if (value == null || value.isBlank()) {
            throw new InvalidEmailException();
        }
        // Add regex validation if needed
    }
}
