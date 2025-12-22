package ecomhub.authservice.infrastructure.inbound.web.swagger.config;

import ecomhub.authservice.infrastructure.inbound.web.swagger.annotation.ErrorResponse;
import ecomhub.authservice.infrastructure.inbound.web.swagger.annotation.StandardApiResponses;
import ecomhub.authservice.infrastructure.inbound.web.swagger.annotation.SuccessfulResponse;
import io.swagger.v3.core.converter.AnnotatedType;
import io.swagger.v3.core.converter.ModelConverters;
import io.swagger.v3.core.converter.ResolvedSchema;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.media.ArraySchema;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.responses.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.web.method.HandlerMethod;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

@Slf4j
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
        Schema<?> apiResponseSchema = resolveSchema(ecomhub.authservice.common.dto.response.ApiResponse.class);
        if (annotation != null) {
            setSuccessfulResponse(operation, annotation.successExample(), apiResponseSchema);
            setErrorResponse(operation, apiResponseSchema, annotation.errorExamples());
        }
        return operation;
    }

    private void setSuccessfulResponse(Operation operation, SuccessfulResponse successfulResponse, Schema<?> apiResponseSchema) {
        Schema<?> successApiResponseSchema = new Schema<>();
        if (apiResponseSchema.getProperties() != null) {
            apiResponseSchema.getProperties().forEach(successApiResponseSchema::addProperty);
        }

        if (successfulResponse.hasData()) {
            Schema<?> dataSchema = resolveSchema(successfulResponse.data());
            if (successfulResponse.isList()) {
                ArraySchema arraySchema = new ArraySchema();
                arraySchema.setItems(dataSchema);
                successApiResponseSchema.addProperty("data", arraySchema);
            } else {
                successApiResponseSchema.addProperty("data", dataSchema);
            }
        }

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

    private Schema<?> resolveSchema(Class<?> clazz) {
        ModelConverters.getInstance().read(clazz);

        ResolvedSchema resolved = ModelConverters.getInstance()
                .resolveAsResolvedSchema(new AnnotatedType(clazz));
        Schema<?> schema = resolved.schema;

        if (schema.getProperties() != null) {
            schema.getProperties().forEach((propertyName, propertySchema) -> {
                try {
                    Type genericType = clazz.getDeclaredField(propertyName).getGenericType();
                    if (propertySchema instanceof ArraySchema arraySchema && arraySchema.getItems() == null) {
                        if (genericType instanceof ParameterizedType pt) {
                            Type listType = pt.getActualTypeArguments()[0];
                            if (listType instanceof Class<?> listClass) {
                                arraySchema.setItems(resolveSchema(listClass));
                            }
                        }
                    }

                } catch (NoSuchFieldException e) {
                    log.warn("Cannot resolve schema for property '{}' of class {}", propertyName, clazz.getName());
                }
            });
        }

        return schema;
    }
}