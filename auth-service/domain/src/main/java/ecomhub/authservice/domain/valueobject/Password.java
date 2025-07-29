package ecomhub.authservice.domain.valueobject;

import ecomhub.authservice.common.exception.concrete.valueobject.password.MissingPasswordException;
import ecomhub.authservice.domain.service.PasswordHashService;

public class Password {
    private final String hashedValue;

    public Password(String rawValue, PasswordHashService hashService) {
        if (rawValue == null || rawValue.isBlank())
            throw new MissingPasswordException();
        this.hashedValue = hashService.hash(rawValue);
    }

    public Password(String hashedValue) {
        if (hashedValue == null || hashedValue.isBlank())
            throw new MissingPasswordException();
        this.hashedValue = hashedValue;
    }


    public String getHashedValue() {
        return hashedValue;
    }

    public boolean verify(String rawValue, PasswordHashService hashService) {
        return hashService.verify(hashedValue, rawValue);
    }
}
