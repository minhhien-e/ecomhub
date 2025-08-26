package ecomhub.authservice.infrastructure.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenAPIConfiguration {
    @Bean
    public OpenAPI defineOpenApi() {
        Server server = new Server();
        server.setUrl("http://localhost:8081");
        server.setDescription("Development");
        Contact myContact = new Contact();
        myContact.setName("minhhien-e");
        myContact.setEmail("minhhien6634@gmail.com");
        Info information = new Info()
                .title("Authorization Service API")
                .version("1.0")
                .description("This API exposes endpoints to manage accounts and roles in the application.")
                .contact(myContact);
        return new OpenAPI().info(information).servers(List.of(server));
    }
    @Bean
    public OperationCustomizer swaggerOperationCustomizer() {
        return new SwaggerOperationCustomizer();
    }
}
