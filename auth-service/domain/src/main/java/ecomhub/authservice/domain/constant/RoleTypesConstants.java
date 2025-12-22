package ecomhub.authservice.domain.constant;

import java.util.Set;

public class RoleTypesConstants {
    public static final String SYSTEM = "SYSTEM";
    public static final String USER = "USER";
    public static final Set<String> TYPES = Set.of(SYSTEM, USER);

    public static boolean isValid(String type) {
        return TYPES.contains(type);
    }

}
