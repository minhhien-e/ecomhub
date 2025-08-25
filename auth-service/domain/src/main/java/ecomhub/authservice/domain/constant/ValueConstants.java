package ecomhub.authservice.domain.constant;

import java.util.HashSet;
import java.util.Set;

public abstract class ValueConstants {
    protected final static Set<String> VALUES= new HashSet<>();

    public static boolean isValid(String value) {
        return VALUES.contains(value.toUpperCase());
    }
}
