package ecomhub.authservice.common.exception.concrete.role;

import ecomhub.authservice.common.exception.abstracts.RequiredFieldMissingException;

public class MissingPermissionException extends RequiredFieldMissingException {
    public MissingPermissionException() {
        super("quyền hạn khi gán vai trò cho người dùng.");
    }}
