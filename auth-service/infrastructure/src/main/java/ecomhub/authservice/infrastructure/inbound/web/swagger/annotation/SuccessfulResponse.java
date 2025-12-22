package ecomhub.authservice.infrastructure.inbound.web.swagger.annotation;


import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({})
@Retention(RetentionPolicy.RUNTIME)
public @interface SuccessfulResponse {
    String message();
    Class<?> data();
    boolean isList() default false;
    boolean hasData() default true;
}
