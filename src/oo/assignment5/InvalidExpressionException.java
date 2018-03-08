package oo.assignment5;

/**
 * @author Ciske Harsema - s1010048
 * @author Michiel Verloop - s1009995
 */
public class InvalidExpressionException extends RuntimeException {

    public InvalidExpressionException(String message) {
        super(message);
    }

    public InvalidExpressionException(String format, Object... args) {
        super(String.format(format, args));
    }
}
