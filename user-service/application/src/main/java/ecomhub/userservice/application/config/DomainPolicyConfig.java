package ecomhub.userservice.application.config;

import ecomhub.userservice.domain.policy.UserAccessPolicy;
import ecomhub.userservice.domain.policy.UserCreationPolicy;
import ecomhub.userservice.domain.repository.UserRepository;
import ecomhub.userservice.domain.service.UserAccessPolicyImpl;
import ecomhub.userservice.domain.service.UserCreationPolicyImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DomainPolicyConfig {
    @Bean
    public UserCreationPolicy userCreationPolicy(UserRepository userRepository) {
        return new UserCreationPolicyImpl(userRepository);
    }

    @Bean
    public UserAccessPolicy userAccessPolicy() {
        return new UserAccessPolicyImpl();
    }
}
