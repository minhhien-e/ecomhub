package ecomhub.authservice.domain.constant;

import java.util.Set;

public class RoleStatusConstants extends ValueConstants {
    public static final String ACTIVE = "ACTIVE";
    public static final String INACTIVE = "INACTIVE";
    public static final String ARCHIVED = "ARCHIVED";
    public static final String DELETED = "DELETED";

    static {
        VALUES.addAll(Set.of(ACTIVE, INACTIVE, ARCHIVED, DELETED));
    }
}
