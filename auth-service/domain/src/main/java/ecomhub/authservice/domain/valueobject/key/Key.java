package ecomhub.authservice.domain.valueobject.key;

import java.util.Objects;

public abstract class Key {
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
        Key key = (Key) o;
        return String.CASE_INSENSITIVE_ORDER
                .compare(value, key.value) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value.toLowerCase());
    }
}
