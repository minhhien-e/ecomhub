package ecomhub.authservice.domain.valueobject;

import ecomhub.authservice.common.exception.concrete.valueobject.password.MissingPasswordException;
import ecomhub.authservice.domain.service.abstracts.PasswordHashService;

public class Password {
    private final String hashedValue;

    public Password(String hashedValue) {
        if (hashedValue == null || hashedValue.isBlank())
            throw new MissingPasswordException();
        this.hashedValue = hashedValue;
    }


    public String getHashedValue() {
        return hashedValue;
    }
}
