package ecomhub.authservice.infrastructure.inbound.web.annotations;


import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({})
@Retention(RetentionPolicy.RUNTIME)
public @interface SuccessfulResponse {
    String message();
    Class<?> data();
}
