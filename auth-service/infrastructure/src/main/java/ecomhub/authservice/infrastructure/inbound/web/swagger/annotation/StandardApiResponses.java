package ecomhub.authservice.infrastructure.inbound.web.swagger.annotation;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface StandardApiResponses {
    SuccessfulResponse successExample();

    ErrorResponse[] errorExamples() default {};
}
