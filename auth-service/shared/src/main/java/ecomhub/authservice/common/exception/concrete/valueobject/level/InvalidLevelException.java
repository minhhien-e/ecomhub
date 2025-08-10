package ecomhub.authservice.common.exception.concrete.valueobject.level;

import ecomhub.authservice.common.exception.abstracts.BusinessRuleViolationException;

public class InvalidLevelException extends BusinessRuleViolationException {
    public InvalidLevelException() {
        super("Giá trị cấp độ vai trò không hợp lệ");
    }
}
