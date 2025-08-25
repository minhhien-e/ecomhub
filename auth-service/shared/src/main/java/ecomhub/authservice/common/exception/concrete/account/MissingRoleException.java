package ecomhub.authservice.common.exception.concrete.account;

import ecomhub.authservice.common.exception.abstracts.RequiredFieldMissingException;

public class MissingRoleException extends RequiredFieldMissingException {
    public MissingRoleException() {
        super("Please select a role to assign to the account",true);
    }
}
