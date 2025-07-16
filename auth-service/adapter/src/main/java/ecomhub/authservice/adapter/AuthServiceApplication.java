package ecomhub.authservice.adapter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(
        scanBasePackages = {
                "ecomhub.authservice.adapter",
                "ecomhub.authservice.application",
                "ecomhub.authservice.infrastructure"
        }
)
@EnableJpaRepositories(basePackages = "ecomhub.authservice.infrastructure.data.persistence.repository")
@EntityScan(basePackages = "ecomhub.authservice.infrastructure.data.persistence.entity")
@EnableJpaAuditing
public class AuthServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthServiceApplication.class, args);
    }

}
