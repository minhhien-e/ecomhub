package ecomhub.authservice.domain.valueobject.name;

import ecomhub.authservice.common.exception.concrete.valueobject.name.MissingNameException;
import ecomhub.authservice.common.utils.StringUtils;

public class RoleName extends Name {
    public RoleName(String value) {
        if (StringUtils.isNullOrBlank(value))
            throw new MissingNameException("role");
        super.setValue(value);
    }
}
