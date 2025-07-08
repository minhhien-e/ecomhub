package ecomhub.authservice.webapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(
        scanBasePackages = {
                "ecomhub.authservice.webapi",
                "ecomhub.authservice.adapter",
                "ecomhub.authservice.application",
                "ecomhub.authservice.domain"
        }
)
@EnableJpaAuditing
@EnableJpaRepositories(basePackages = {"ecomhub.authservice.adapter.output.persistence.repositories"})
@EntityScan(basePackages = "ecomhub.authservice.adapter.output.persistence.entity")
public class WebapiApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebapiApplication.class, args);
    }

}
