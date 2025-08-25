package ecomhub.authservice.domain.valueobject.name;

import java.util.Objects;

public abstract class Name {
    private String value;


    protected void setValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Name username = (Name) o;
        return String.CASE_INSENSITIVE_ORDER
                .compare(value, username.value) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value.toLowerCase());
    }
}
