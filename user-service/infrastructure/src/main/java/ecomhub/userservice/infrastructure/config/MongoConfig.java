package ecomhub.userservice.infrastructure.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "ecomhub.userservice.infrastructure.persistence.repository")
public class MongoConfig {
}
