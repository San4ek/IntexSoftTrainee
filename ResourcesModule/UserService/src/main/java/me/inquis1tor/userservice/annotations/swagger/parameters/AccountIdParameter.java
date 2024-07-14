package me.inquis1tor.userservice.annotations.swagger.parameters;

import io.swagger.v3.oas.annotations.Parameter;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;

@Target(METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Parameter(name = "id",
        description = "Account id",
        required = true)
public @interface AccountIdParameter {
}
