package ecomhub.salerservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;


@SpringBootApplication
@EnableMongoAuditing
public class SalerServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SalerServiceApplication.class, args);
    }

}
