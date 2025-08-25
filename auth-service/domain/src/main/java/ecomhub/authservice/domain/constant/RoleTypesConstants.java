package ecomhub.authservice.domain.constant;

import java.util.Set;

public class RoleTypesConstants extends ValueConstants {
    public static final String ADMIN = "ADMIN";
    public static final String CUSTOMER = "CUSTOMER";
    public static final String SELLER = "SELLER";

    static {
        VALUES.addAll(Set.of(ADMIN, CUSTOMER, SELLER));
    }
}
