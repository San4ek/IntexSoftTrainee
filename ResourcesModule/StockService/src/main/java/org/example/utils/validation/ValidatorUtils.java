package org.example.utils.validation;

import jakarta.annotation.Nullable;
import org.example.exceptions.InvalidObjectException;

/**
 * Utility class for validating expressions.
 */
public class ValidatorUtils {

    /**
     * Checks if the given expression is true.
     * If the expression is false, throws an InvalidObjectException with the specified message.
     *
     * @param expression the expression to check
     * @param message the message to include in the exception if the expression is false
     * @throws InvalidObjectException if the expression is false
     */
    public static void checkTrue(boolean expression, @Nullable String message) {
        if (!expression) {
            throw new InvalidObjectException(message);
        }
    }

    /**
     * Checks if the given expression is true.
     * If the expression is false, throws an InvalidObjectException without message.
     *
     * @param expression the expression to check
     * @throws InvalidObjectException if the expression is false
     */
    public static void checkTrue(boolean expression) {
        if (!expression) {
            throw new InvalidObjectException();
        }
    }

    /**
     * Checks if the given expression is false.
     * If the expression is true, throws an InvalidObjectException with the specified message.
     *
     * @param expression the expression to check
     * @param message the message to include in the exception if the expression is true
     * @throws InvalidObjectException if the expression is true
     */
    public static void checkFalse(boolean expression, @Nullable String message) {
        if (expression) {
            throw new InvalidObjectException(message);
        }
    }

    /**
     * Checks if the given expression is false.
     * If the expression is true, throws an InvalidObjectException with no message.
     *
     * @param expression the expression to check
     * @throws InvalidObjectException if the expression is true
     */
    public static void checkFalse(boolean expression) {
        if (expression) {
            throw new InvalidObjectException();
        }
    }
}
