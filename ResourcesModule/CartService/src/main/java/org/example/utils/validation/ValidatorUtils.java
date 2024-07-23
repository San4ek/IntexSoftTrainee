package org.example.utils.validation;

import lombok.SneakyThrows;
import org.example.exceptions.InvalidObjectException;


public class ValidatorUtils {

    @SneakyThrows
    public static void checkTrue(boolean expression, String message) {
        if (!expression) {
            throw new InvalidObjectException(message);
        }
    }

    @SneakyThrows
    public static void checkTrue(boolean expression) {
        if (!expression) {
            throw new InvalidObjectException();
        }
    }

    @SneakyThrows
    public static void checkFalse(boolean expression, String message) {
        if (expression) {
            throw new InvalidObjectException(message);
        }
    }

    @SneakyThrows
    public static void checkFalse(boolean expression) {
        if (expression) {
            throw new InvalidObjectException();
        }
    }


}
