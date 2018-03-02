package oo.utils;

/**
 * @author Ciske Harsema - s1010048
 * @author Michiel Verloop - s1009995
 */
public class Contract {
    public static void isTrue(boolean condition, String errorFormat, Object... args) {
        if(!condition) {
            fail(errorFormat, args);
        }
    }

    public static void isFalse(boolean condition, String errorFormat, Object... args) {
        if(condition) {
            fail(errorFormat, args);
        }
    }

    public static void notNull(Object object, String errorFormat, Object... args) {
        if(object == null) {
            fail(errorFormat, args);
        }
    }

    /**
     * Check if the value is within the range [min,max)
     * @param value The value to check.
     * @param min The minimal value (inclusive).
     * @param max The maximal value (exclusive).
     * @param errorFormat The format of the error message passed to String.format.
     * @param args The arguments of the error message passed to String.format.
     * @param <T> The value type.
     */
    public static <T extends Comparable<T>> void inRange(T value, T min, T max, String errorFormat, Object... args) {
        if(value.compareTo(min) < 0 || value.compareTo(max) >= 0) {
            fail(errorFormat, args);
        }
    }

    private static void fail(String errorFormat, Object... args) {
        throw new ContractViolationException(String.format(errorFormat, args));
    }
}
