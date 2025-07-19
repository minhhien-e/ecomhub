package ecomhub.authservice.common.utils;

public class StringUtils {
    public static boolean isEmpty(String value) {
        return value.trim().isEmpty();
    }

    public static String buildStringFormat(String format, Object... args) {
        return String.format(format, args);
    }


}
