package ecomhub.authservice.common.exception;

public class AccountNotFoundException extends BusinessException {
    public AccountNotFoundException(String message) {
        super(message);
    }

    public AccountNotFoundException() {
        super("Tài khoản không tồn tại");
    }
}
