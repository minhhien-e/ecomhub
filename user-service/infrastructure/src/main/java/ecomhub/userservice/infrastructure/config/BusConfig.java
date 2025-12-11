package ecomhub.userservice.infrastructure.config;

import io.github.mediatR.api.Bus;
import io.github.mediatR.api.RequestHandler;
import io.github.mediatR.core.BusImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BusConfig {
    @Bean
    public Bus bus(ApplicationContext context) {
        return new BusImpl(context.getBeansOfType(RequestHandler.class));
    }
}
