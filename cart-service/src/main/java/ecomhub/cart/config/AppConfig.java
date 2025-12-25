package ecomhub.cart.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Getter
@Configuration
public class AppConfig {



    @Value("${services.product.url:http://localhost:8081/api/product}")
    private String productServiceUrl;


    @Bean
    public RestTemplate restTemplate() {
        System.out.println("Configuring RestTemplate. Product Service Base URL: " + productServiceUrl);

        return new RestTemplate();
    }

}