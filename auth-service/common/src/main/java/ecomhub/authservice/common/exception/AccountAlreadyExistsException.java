package ecomhub.authservice.common.exception;

public class AccountAlreadyExistsException extends RuntimeException {
    public AccountAlreadyExistsException(String message) {
        super(message);
    }

    public AccountAlreadyExistsException() {
        super("Tài khoản đã tồn tại");
    }
}
