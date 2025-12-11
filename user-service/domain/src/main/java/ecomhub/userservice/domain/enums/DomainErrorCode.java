package ecomhub.userservice.domain.enums;

public enum DomainErrorCode {
    USER_NOT_FOUND(100),
    INVALID_EMAIL(101),
    INVALID_USERNAME(102),
    INVALID_PASSWORD(103),
    USER_ALREADY_EXISTS(104),
    ACCESS_DENIED(105),
    USER_SETTING_NOT_FOUND(106);

    private final Integer value;

    DomainErrorCode(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
}
