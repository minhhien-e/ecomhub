package ecomhub.authservice.common.exception.concrete.valueobject.email;

import ecomhub.authservice.common.exception.abstracts.InvalidFormatException;

public class InvalidEmailFormatException extends InvalidFormatException {
    public InvalidEmailFormatException(String email) {
        super("Định dạng email không hợp lệ: '" + email + "'. Vui lòng nhập đúng định dạng email.");
    }
}
