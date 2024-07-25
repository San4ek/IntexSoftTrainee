package org.example.utils.validation;

import lombok.SneakyThrows;
import org.example.exceptions.InvalidObjectException;

/**
 * Utils class for validation with boolean operations
 */
public class ValidatorUtils {

    @SneakyThrows
    public static void checkTrue(final Boolean expression, final String message) {
        if (!expression) {
            throw new InvalidObjectException(message);
        }
    }

    @SneakyThrows
    public static void checkTrue(final Boolean expression) {
        if (!expression) {
            throw new InvalidObjectException();
        }
    }

    @SneakyThrows
    public static void checkFalse(final Boolean expression, final String message) {
        if (expression) {
            throw new InvalidObjectException(message);
        }
    }

    @SneakyThrows
    public static void checkFalse(final Boolean expression) {
        if (expression) {
            throw new InvalidObjectException();
        }
    }
}
