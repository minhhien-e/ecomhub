package ecomhub.authservice.domain.valueobject.name;

import ecomhub.authservice.common.exception.concrete.valueobject.name.MissingNameException;
import ecomhub.authservice.common.utils.StringUtils;

public class PermissionName extends Name {
    public PermissionName(String value) {
        if (StringUtils.isNullOrBlank(value))
            throw new MissingNameException("permission");
        super.setValue(value);
    }
}