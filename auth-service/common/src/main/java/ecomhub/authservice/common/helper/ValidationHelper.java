package ecomhub.authservice.common.helper;

import ecomhub.authservice.common.exception.ValidationException;
import ecomhub.authservice.common.utils.StringUtils;

public class ValidationHelper {
    public static void requireNonBlank(String field, String value) {
        if (StringUtils.isNullOrEmpty(value)) {
            throw new ValidationException(String.format("%s không thể null hoặc rỗng", field));
        }
    }
}
