package ecomhub.userservice.domain.exception.usersetting;

import ecomhub.userservice.domain.enums.DomainErrorCode;
import ecomhub.userservice.domain.exception.base.DomainException;

import java.util.UUID;

public class UserSettingNotFoundException extends DomainException {
    public UserSettingNotFoundException(UUID userId) {
        super("User settings for user with id " + userId + " not found", DomainErrorCode.USER_SETTING_NOT_FOUND);
    }
}
