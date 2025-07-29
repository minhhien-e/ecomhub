package ecomhub.authservice.common.exception.concrete.account;

import ecomhub.authservice.common.exception.abstracts.RequiredFieldMissingException;

public class MissingRoleException extends RequiredFieldMissingException {
    public MissingRoleException() {
        super("vai trò khi gán vai trò cho người dùng");
    }
}
