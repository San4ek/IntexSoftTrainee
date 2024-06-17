package me.inquis1tor.userservice.annotations.swagger.requests;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.PARAMETER;

@Target(PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@RequestBody(useParameterTypeSchema = true)
public @interface SwaggerRequestBody {

    @AliasFor(annotation = RequestBody.class)
    String description() default "";
}
