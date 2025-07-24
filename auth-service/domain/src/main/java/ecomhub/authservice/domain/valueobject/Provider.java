package ecomhub.authservice.domain.valueobject;

import ecomhub.authservice.common.enums.ProviderType;
import ecomhub.authservice.common.exception.concrete.valueobject.provider.MissingProviderException;
import ecomhub.authservice.common.exception.concrete.valueobject.provider.ProviderNotSupportException;

import java.util.Arrays;
import java.util.Objects;

public class Provider {
    private String value;

    public Provider(String value) {
        if (value == null || value.isBlank())
            throw new MissingProviderException();
        if (Arrays.stream(ProviderType.values())
                .noneMatch(provider -> provider.name().equalsIgnoreCase(value)))
            throw new ProviderNotSupportException(value);
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public boolean isSame(Provider provider) {
        return this.equals(provider);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Provider provider = (Provider) o;
        return String.CASE_INSENSITIVE_ORDER
                .compare(value, provider.value) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value.toLowerCase());
    }
}
