package ecomhub.authservice.domain.valueobject;

import ecomhub.authservice.common.exception.concrete.role.MissingPermissionException;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        PermissionKey that = (PermissionKey) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}
