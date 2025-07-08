package ecomhub.authservice.domain.service;

public interface PasswordHashService {
    String hash(String password);
    boolean verify(String password, String hash);
}
