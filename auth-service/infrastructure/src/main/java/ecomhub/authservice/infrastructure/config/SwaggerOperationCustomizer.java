package ecomhub.authservice.infrastructure.config;

import ecomhub.authservice.infrastructure.inbound.web.annotations.ErrorResponse;
import ecomhub.authservice.infrastructure.inbound.web.annotations.StandardApiResponses;
import io.swagger.v3.core.converter.ModelConverters;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.responses.ApiResponse;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.web.method.HandlerMethod;

import java.util.HashMap;
import java.util.Map;


public class SwaggerOperationCustomizer implements OperationCustomizer {
    private static final String FORMAT = """
            {
              "statusCode": %s,
              "errorCode": "%s",
              "message": "%s",
              "data": %s
            }
            """;

    @Override
    public Operation customize(Operation operation, HandlerMethod handlerMethod) {
        var annotation = handlerMethod.getMethodAnnotation(StandardApiResponses.class);
        var schemas = getSchemas(ecomhub.authservice.common.dto.response.ApiResponse.class);
        Schema<?> apiResponseSchema = schemas.get(ecomhub.authservice.common.dto.response.ApiResponse.class.getSimpleName());
        if (annotation != null) {
            Class<?> successDataClass = annotation.successExample().data();
            setSuccessfulResponse(operation, successDataClass, apiResponseSchema);
            setErrorResponse(operation, apiResponseSchema, annotation.errorExamples());
        }
        return operation;
    }

    private void setSuccessfulResponse(Operation operation, Class<?> successDataClass, Schema<?> apiResponseSchema) {
        Schema<?> successApiResponseSchema = new Schema<>();
        apiResponseSchema.getProperties().forEach(successApiResponseSchema::addProperty);
        Schema<?> successDataSchema = getSchemas(successDataClass)
                .get(successDataClass.getSimpleName());
        successApiResponseSchema.addProperty("data", successDataSchema);
        operation.getResponses().addApiResponse("200",
                new ApiResponse()
                        .description("Successful")
                        .content(new Content()
                                .addMediaType("application/json",
                                        new MediaType().schema(successApiResponseSchema))
                        )
        );
    }

    private void setErrorResponse(Operation operation, Schema<?> apiResponseSchema, ErrorResponse[] errorResponses) {
        for (var errorResponse : errorResponses) {
            String exampleJson = String.format(FORMAT, errorResponse.statusCode(), errorResponse.code(), errorResponse.message(), "null");
            operation.getResponses().addApiResponse(errorResponse.statusCode(),
                    new ApiResponse()
                            .description(errorResponse.message())
                            .content(new Content()
                                    .addMediaType("application/json",
                                            new MediaType()
                                                    .schema(apiResponseSchema)
                                                    .example(exampleJson))
                            )
            );
        }
    }

    private Map<String, Schema> getSchemas(Class<?> clazz) {
        return ModelConverters.getInstance().readAll(clazz);
    }
}
