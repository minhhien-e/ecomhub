package ecomhub.userservice.domain.policy;

public interface UserCreationPolicy {
    void ensureCanCreate(String username, String email);
}
