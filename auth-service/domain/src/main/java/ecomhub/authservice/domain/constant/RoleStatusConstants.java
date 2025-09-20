package ecomhub.authservice.domain.constant;

import java.util.Set;

public class RoleStatusConstants {
    public static final String ACTIVE = "ACTIVE";
    public static final String INACTIVE = "INACTIVE";
    public static final String ARCHIVED = "ARCHIVED";
    public static final String DELETED = "DELETED";

    public static final Set<String> STATUS = Set.of(ACTIVE, INACTIVE, ARCHIVED, DELETED);

    public static boolean isValid(String type) {
        return STATUS.contains(type);
    }
}
