package ecomhub.authservice.common.exception;

public class RoleAlreadyExistsException extends RuntimeException {
    public RoleAlreadyExistsException(String message) {
        super(message);
    }

    public RoleAlreadyExistsException() {
        super("Role đã tồn tại");
    }

}
