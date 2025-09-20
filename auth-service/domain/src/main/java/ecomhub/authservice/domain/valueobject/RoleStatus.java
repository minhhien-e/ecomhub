package ecomhub.authservice.domain.valueobject;

import ecomhub.authservice.common.exception.concrete.valueobject.status.MissingStatusException;
import ecomhub.authservice.common.exception.concrete.valueobject.status.InvalidStatusException;
import ecomhub.authservice.common.utils.StringUtils;
import ecomhub.authservice.domain.constant.RoleStatusConstants;

import java.util.Objects;

public class RoleStatus {
    private final String value;

    public RoleStatus(String value) {
        if (StringUtils.isNullOrBlank(value))
            throw new MissingStatusException("role");
        if (!RoleStatusConstants.isValid(value))
            throw new InvalidStatusException(value, "role");
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        RoleStatus status = (RoleStatus) o;
        return String.CASE_INSENSITIVE_ORDER
                .compare(value, status.value) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value.toLowerCase());
    }
}
