package ecomhub.authservice.common.exception.concrete.valueobject.phonenumber;

import ecomhub.authservice.common.exception.abstracts.InvalidFormatException;

public class InvalidPhoneNumberFormatException extends InvalidFormatException {
    public InvalidPhoneNumberFormatException(String phoneNumber) {
        super("Invalid phone number format: '" + phoneNumber + "'. Please enter a valid phone number.");
    }
}
