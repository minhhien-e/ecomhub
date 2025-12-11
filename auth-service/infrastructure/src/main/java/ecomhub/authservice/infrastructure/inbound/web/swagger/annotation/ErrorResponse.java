package ecomhub.authservice.infrastructure.inbound.web.swagger.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({})
@Retention(RetentionPolicy.RUNTIME)
public @interface ErrorResponse {
    String statusCode();
    String code();
    String message();
}
