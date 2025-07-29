package ecomhub.authservice.common.exception.concrete.valueobject.phonenumber;

import ecomhub.authservice.common.exception.abstracts.InvalidFormatException;

public class InvalidPhoneNumberFormatException extends InvalidFormatException {
    public InvalidPhoneNumberFormatException(String email) {
        super("Định dạng số điện thoại không hợp lệ: '" + email + "'. Vui lòng nhập đúng định dạng số điện thoai.");
    }
}
