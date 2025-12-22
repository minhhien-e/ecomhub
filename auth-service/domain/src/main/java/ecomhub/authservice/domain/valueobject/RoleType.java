package ecomhub.authservice.domain.valueobject;

import ecomhub.authservice.common.exception.concrete.valueobject.type.InvalidTypeException;
import ecomhub.authservice.common.exception.concrete.valueobject.type.MissingTypeException;
import ecomhub.authservice.common.utils.StringUtils;
import ecomhub.authservice.domain.constant.RoleTypesConstants;

import java.util.Objects;

public class RoleType {
    private final String value;

    public RoleType(String value) {
        if (StringUtils.isNullOrBlank(value))
            throw new MissingTypeException("role");
        if (!RoleTypesConstants.isValid(value))
            throw new InvalidTypeException(value, "role");
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        RoleType type = (RoleType) o;
        return String.CASE_INSENSITIVE_ORDER
                .compare(value, type.value) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value.toLowerCase());
    }
}
