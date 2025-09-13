package ecomhub.authservice.infrastructure.inbound.web.swagger.config;

import ecomhub.authservice.common.dto.response.ApiResponse;
import ecomhub.authservice.common.dto.response.PermissionResponse;
import ecomhub.authservice.common.dto.response.RoleResponse;
import io.swagger.v3.core.converter.AnnotatedType;
import io.swagger.v3.core.converter.ModelConverters;
import io.swagger.v3.core.converter.ResolvedSchema;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@OpenAPIDefinition(
        info = @io.swagger.v3.oas.annotations.info.Info(title = "EcomHub API", version = "v1"),
        security = {
                @SecurityRequirement(name = "bearerAuth")
        }
)
@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT"
)
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
        var openApi = new OpenAPI().info(information).servers(List.of(server));
        openApi.setComponents(new Components());
        addSchema(PermissionResponse.class, openApi);
        addSchema(RoleResponse.class, openApi);
        addSchema(ApiResponse.class, openApi);
        return openApi;
    }

    @Bean
    public OperationCustomizer swaggerOperationCustomizer() {
        return new SwaggerOperationCustomizer();
    }

    private void addSchema(Class<?> clazz, OpenAPI openAPI) {
        ResolvedSchema resolved = ModelConverters.getInstance()
                .resolveAsResolvedSchema(new AnnotatedType(clazz));
        Schema<?> schema = resolved.schema;
        openAPI.getComponents()
                .addSchemas(clazz.getSimpleName(), schema);
    }
}
