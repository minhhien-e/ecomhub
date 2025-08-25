package ecomhub.authservice.infrastructure.config;

import ecomhub.authservice.domain.service.abstracts.AccountService;
import ecomhub.authservice.domain.service.abstracts.PasswordHashService;
import ecomhub.authservice.domain.service.abstracts.RoleService;
import ecomhub.authservice.domain.service.impl.AccountServiceImpl;
import ecomhub.authservice.domain.service.impl.RoleServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public AccountService accountService(PasswordHashService passwordHashService){
        return new AccountServiceImpl(passwordHashService);
    }
    @Bean
    public RoleService roleService(){
        return new RoleServiceImpl();
    }
}
