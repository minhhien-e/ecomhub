package ecomhub.authservice.domain.valueobject.name;

import ecomhub.authservice.common.exception.concrete.valueobject.username.MissingUsernameException;
import ecomhub.authservice.common.utils.StringUtils;

public class Username extends Name {

    public Username(String value) {
        if (StringUtils.isNullOrBlank(value))
            throw new MissingUsernameException();
        super.setValue(value);
    }

}
