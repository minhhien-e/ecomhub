package ecomhub.authservice.common.exception;

public class PermissionAlreadyExistsException extends RuntimeException {
    public PermissionAlreadyExistsException(String message) {
        super(message);
    }

    public PermissionAlreadyExistsException() {
        super("Quyền này đã tồn tại");
    }

}
