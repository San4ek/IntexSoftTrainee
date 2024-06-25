package me.inquis1tor.userservice.annotations.swagger.parameters;

import io.swagger.v3.oas.annotations.Parameter;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;

@Target(METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Parameter(name = "adminId",
        description = "Administrator id",
        required = true)
public @interface AdminIdParameter {
}
