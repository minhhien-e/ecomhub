package ecomhub.authservice.domain.valueobject.key;

import ecomhub.authservice.common.exception.concrete.role.MissingKeyException;
import ecomhub.authservice.common.utils.StringUtils;

public class PermissionKey extends Key {

    public PermissionKey(String value) {
        if (StringUtils.isNullOrBlank(value))
            throw new MissingKeyException("permission");
        setValue(value);
    }
}
