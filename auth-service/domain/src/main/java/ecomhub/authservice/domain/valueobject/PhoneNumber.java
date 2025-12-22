package ecomhub.authservice.domain.valueobject;

import ecomhub.authservice.common.exception.concrete.valueobject.phonenumber.InvalidPhoneNumberFormatException;
import ecomhub.authservice.common.exception.concrete.valueobject.phonenumber.MissingPhoneNumberException;
import ecomhub.authservice.common.utils.StringUtils;

import java.util.Objects;

public class PhoneNumber {
    private static final String VIETNAM_PHONE_NUMBER_REGEX = "^(0|\\+84)[3|5|7|8|9]\\d{8}$";
    private final String value;

    public PhoneNumber(String value) {
        if (StringUtils.isNullOrBlank(value))
            throw new MissingPhoneNumberException();
        if (!value.matches(VIETNAM_PHONE_NUMBER_REGEX))
            throw new InvalidPhoneNumberFormatException(value);
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        PhoneNumber phoneNumber = (PhoneNumber) o;
        return String.CASE_INSENSITIVE_ORDER
                .compare(value, phoneNumber.value) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value.toLowerCase());
    }
}
