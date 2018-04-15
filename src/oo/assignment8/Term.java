package oo.assignment8;

import java.util.Scanner;

/**
 * For representing terms in a polynomial.
 *
 * @author Sjaak Smetsers
 * @version 1.0
 * @date 15-02-2012
 */
public class Term {
    /**
     * Each term consists of a coefficient and an exponent
     */
    private double coefficient;
    private int exponent;

    /**
     * a get-method for the exponent
     *
     * @return exponent
     */
    public int getExp() {
        return exponent;
    }

    /**
     * a get-method for the coefficient
     *
     * @return coefficient
     */
    public double getCoef() {
        return coefficient;
    }

    /**
     * A two-argument constructor
     *
     * @param c the value for the coefficient
     * @param e the value for the exponent
     */
    public Term(double c, int e) {
        coefficient = c;
        exponent = e;
    }

    /**
     * A copy-constructor
     *
     * @param t the term to be copied
     */
    public Term(Term t) {
        this(t.coefficient, t.exponent);
    }

    /**
     * For adding two terms with equal exponents
     *
     * @param t the term added to this
     * @require exponent == t.exponent
     */
    public void plus(Term t) {
        if(exponent != t.exponent) {
            throw new IllegalArgumentException("exponents must be equal");
        }

        coefficient += t.coefficient;
    }

    /**
     * For multiplying two terms
     *
     * @param t the multiplier
     */
    public void times(Term t) {
        exponent += t.exponent;
        coefficient *= t.coefficient;
    }

    /**
     * For negating terms
     */
    public void neg() {
        coefficient = -coefficient;
    }
    /**
     * For dividing two terms. Note that dividing by a zero coefficient will produce an infinity
     * coefficient (IEEE 754 standard).
     *
     * @param t the divisor
     */
    public void divide(Term t) {
        if ( exponent < t.exponent ) {
            throw new ArithmeticException( "division error: exponent of divisor too large" );
        }
        exponent -= t.exponent;
        coefficient /= t.coefficient;
    }
    
    /**
     * Converts a term into a readable representation the standard format is
     * %fx^%d
     *
     * @return the string representing the term
     */
    @Override
    public String toString() {
        return exponent > 1 ? String.format("%fx^%d", coefficient, exponent)
                : String.format(exponent == 0 ? "%f" : "%fx", coefficient);
    }

    /**
     * Standard implementation of equality
     */
    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Term) {
            Term other = (Term)obj;

            return exponent == other.exponent && coefficient == other.coefficient;
        } else {
            return false;
        }
    }

    /**
     * A static method for converting scanner input into a term. The expected
     * format is two numbers (coef, exp) separated by whitespaces. The coef
     * should be either an integer or a decimal number. The exp is an integer.
     *
     * @param s the scanner providing the input
     * @return null if no term could be found, the found term otherwise
     */
    public static Term scanTerm(Scanner s) {
        if (s.hasNextDouble()) {
            double coef = s.nextDouble();

            return s.hasNextInt() ? new Term(coef, s.nextInt()) : null;
        }

        return null;
    }
}
