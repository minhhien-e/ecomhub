package ecomhub.authservice.domain.constant;

import java.util.Set;

public class RoleKeyConstants {
    public static final String ADMIN = "ADMIN";
    public static final String CUSTOMER = "CUSTOMER";
    public static final String SELLER = "SELLER";

    public static final Set<String> KEYS = Set.of(ADMIN, CUSTOMER, SELLER);

    public static boolean isValid(String type) {
        return KEYS.contains(type);
    }
}
