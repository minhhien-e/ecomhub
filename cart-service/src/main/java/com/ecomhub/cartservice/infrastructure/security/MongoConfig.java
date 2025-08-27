package com.ecomhub.cartservice.infrastructure.security;// MongoConfig.java
import com.mongodb.client.MongoClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;

@Configuration
public class MongoConfig {

    @Bean
    MongoTransactionManager transactionManager(MongoClient mongoClient) {
        return new MongoTransactionManager(mongoDbFactory(mongoClient));
    }

    private SimpleMongoClientDatabaseFactory mongoDbFactory(MongoClient mongoClient) {
        return new SimpleMongoClientDatabaseFactory(mongoClient, "cartdb");
    }
}
