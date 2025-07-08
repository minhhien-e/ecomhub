package ecomhub.authservice.common.utils;

public class StringUtils {
    public static boolean isNullOrEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }
    public static String buildStringFormat(String format, Object... args){
        return String.format(format, args);
    }

}
