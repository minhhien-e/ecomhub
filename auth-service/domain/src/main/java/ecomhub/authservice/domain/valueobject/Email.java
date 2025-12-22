package ecomhub.authservice.domain.valueobject;

import ecomhub.authservice.common.exception.concrete.valueobject.email.InvalidEmailFormatException;
import ecomhub.authservice.common.exception.concrete.valueobject.email.MissingEmailException;
import ecomhub.authservice.common.utils.StringUtils;

import java.util.Objects;

public class Email {
    private final String value;

    public Email(String value) {
        if (StringUtils.isNullOrBlank(value))
            throw new MissingEmailException();
        if (!value.matches("^[\\w._%+-]+@[\\w.-]+\\.[a-z]+$"))
            throw new InvalidEmailFormatException(value);
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Email email = (Email) o;
        return String.CASE_INSENSITIVE_ORDER
                .compare(value, email.value) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value.toLowerCase());
    }
}
