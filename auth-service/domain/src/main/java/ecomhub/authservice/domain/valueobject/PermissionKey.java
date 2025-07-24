package ecomhub.authservice.domain.valueobject;

import ecomhub.authservice.common.exception.concrete.role.MissingPermissionException;

public class PermissionKey {
    private final String value;

    public PermissionKey(String value) {
        if (value == null || value.isBlank())
            throw new MissingPermissionException();
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
