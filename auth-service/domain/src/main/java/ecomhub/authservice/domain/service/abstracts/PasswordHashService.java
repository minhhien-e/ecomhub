package ecomhub.authservice.domain.service.abstracts;

public interface PasswordHashService {
    String hash(String password);
    boolean verify(String password, String hash);
}
