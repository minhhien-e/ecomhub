package ecomhub.authservice.common.utils;

public class StringUtils {
    public static boolean isNullOrBlank(String value) {
        return value == null || value.isBlank();
    }

    public static String buildStringFormat(String format, Object... args) {
        return String.format(format, args);
    }

}
