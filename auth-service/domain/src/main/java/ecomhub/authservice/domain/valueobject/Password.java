package ecomhub.authservice.domain.valueobject;

import ecomhub.authservice.common.exception.concrete.valueobject.password.MissingPasswordException;
import ecomhub.authservice.common.utils.StringUtils;
import ecomhub.authservice.domain.service.abstracts.PasswordHashService;

public class Password {
    //region Fields
    private final String hashedValue;

    //endregion
    //region Constructors
    public Password(String hashedValue) {
        if (StringUtils.isNullOrBlank(hashedValue))
            throw new MissingPasswordException();
        this.hashedValue = hashedValue;
    }

    //endregion
    //region Getter
    public String getHashedValue() {
        return hashedValue;
    }
    //endregion
}
