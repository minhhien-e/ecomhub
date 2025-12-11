package ecomhub.authservice.domain.valueobject.key;

import ecomhub.authservice.common.exception.concrete.role.MissingKeyException;
import ecomhub.authservice.common.utils.StringUtils;

public class RoleKey extends Key {

    public RoleKey(String value) {
        if (StringUtils.isNullOrBlank(value))
            throw new MissingKeyException("role");
        setValue(value);
    }
}
