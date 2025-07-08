package ecomhub.authservice.adapter.output.service;

import ecomhub.authservice.domain.service.PasswordHashService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BCryptPasswordHashService implements PasswordHashService {
    private final PasswordEncoder passwordEncoder;

    @Override
    public String hash(String password) {
        return passwordEncoder.encode(password);
    }

    @Override
    public boolean verify(String password, String hash) {
        return passwordEncoder.matches(password, hash);
    }
}
