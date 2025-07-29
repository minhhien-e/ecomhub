package ecomhub.authservice.domain.valueobject;

import ecomhub.authservice.common.exception.concrete.valueobject.username.MissingUsernameException;

import java.util.Objects;

public class Username {
    private final String value;

    public Username(String value) {
        if (value == null || value.isBlank())
            throw new MissingUsernameException();
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Username username = (Username) o;
        return String.CASE_INSENSITIVE_ORDER
                .compare(value, username.value) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value.toLowerCase());
    }
}
