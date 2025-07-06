package ecomhub.authservice.common.utils;

public class StringUtils {
    public static boolean isNullOrEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }
}
