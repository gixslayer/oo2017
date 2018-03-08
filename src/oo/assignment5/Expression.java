package oo.assignment5;

import java.util.Map;

/**
 * @author Ciske Harsema - s1010048
 * @author Michiel Verloop - s1009995
 */
public abstract class Expression {

    /**
     * Evaluate the expression.
     * @param store The store to resolve variables with.
     * @return The evaluated expression value.
     */
    public abstract double eval(Map<String, Double> store);

    /**
     * Tries to optimize the expression.
     * @return An equivalent and potentially optimized expression.
     */
    public Expression optimize() {
        return this;
    }
}
