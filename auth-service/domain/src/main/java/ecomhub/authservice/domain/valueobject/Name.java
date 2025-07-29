package ecomhub.authservice.domain.valueobject;

import ecomhub.authservice.common.exception.concrete.valueobject.name.MissingNameException;

public class Name {
    private final String value;

    public Name(String value, String domainName) {
        if (value == null || value.isBlank())
            throw new MissingNameException(domainName);
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
