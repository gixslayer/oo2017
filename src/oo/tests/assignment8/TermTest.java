package oo.tests.assignment8;

import oo.assignment8.Term;
import org.junit.Test;

import java.util.Scanner;

import static org.junit.Assert.*;


/**
 * @author Ciske Harsema - s1010048
 * @author Michiel Verloop - s1009995
 */
public class TermTest {

    @Test
    public void plusEqualExponentsPostitiveCoef() {
        Term a = new Term(1, 2);
        Term b = new Term(2, 2);
        Term expected = new Term(3, 2);

        a.plus(b);

        assertEquals(expected, a);
    }

    @Test
    public void plusEqualExponentsZeroCoef() {
        Term a = new Term(3, 2);
        Term b = new Term(0, 2);
        Term expected = new Term(a.getCoef(), 2);

        a.plus(b);

        assertEquals(expected, a);
    }

    @Test
    public void plusEqualExponentsNegativeCoef() {
        Term a = new Term(1, 2);
        Term b = new Term(-2, 2);
        Term expected = new Term(-1, 2);

        a.plus(b);

        assertEquals(expected, a);
    }

    @Test(expected = IllegalArgumentException.class)
    public void plusDifferentExponents() {
        Term a = new Term(1, 2);
        Term b = new Term(1, 3);

        // Throws exception.
        a.plus(b);
    }

    @Test
    public void timesSelf() {
        Term a = new Term(1, 1);
        Term expected = new Term(1, 2);

        a.times(a);

        assertEquals(expected, a);
    }

    @Test
    public void timesEqualExponents() {
        Term a = new Term(1, 1);
        Term b = new Term(2, 1);
        Term expected = new Term(2, 2);

        a.times(b);

        assertEquals(expected, a);
    }

    @Test
    public void timesDifferentExponents() {
        Term a = new Term(6, 4);
        Term b = new Term(0, 3);
        Term expected = new Term(0, 7);

        a.times(b);

        assertEquals(expected, a);
    }

    @Test
    public void neg() {
        Term a = new Term(3.14, 0);
        Term expected = new Term(-a.getCoef(), a.getExp());

        a.neg();

        assertEquals(expected, a);
    }

    @Test
    public void divideZeroCoef() {
        Term a = new Term(1,2 );
        Term b = new Term(0, 2);

        a.divide(b);

        assertEquals(0, a.getExp());
        assertTrue(Double.isInfinite(a.getCoef()));
    }

    @Test
    public void divideLowerExponent() {
        Term a = new Term(6, 5);
        Term b = new Term (2, 3);
        Term expected = new Term(3, 2);

        a.divide(b);

        assertEquals(expected, a);
    }

    @Test(expected = ArithmeticException.class)
    public void divideHigherExponent() {
        Term a = new Term(1,2 );
        Term b = new Term(6, 5);

        // Throws exception
        a.divide(b);
    }

    @Test
    public void toStringDegree0() {
        Term a = new Term(4, 0);
        String expected = String.format("%f", a.getCoef());

        assertEquals(expected, a.toString());
    }

    @Test
    public void toStringDegree1() {
        Term a = new Term(5, 1);
        String expected = String.format("%fx", a.getCoef());

        assertEquals(expected, a.toString());
    }

    @Test
    public void toStringDegree2() {
        Term a = new Term(6, 2);
        String expected = String.format("%fx^%d", a.getCoef(), a.getExp());

        assertEquals(expected, a.toString());
    }

    @Test
    public void equalsDifferentExponent() {
        Term a = new Term(0, 1);
        Term b = new Term(0, 2);

        assertNotEquals(a, b);
    }

    @Test
    public void equalsDifferentCoef() {
        Term a = new Term(0, 1);
        Term b = new Term(1, 1);

        assertNotEquals(a, b);
    }

    @Test
    public void equalsDifferentExponentAndCoef() {
        Term a = new Term(0, 1);
        Term b = new Term(1, 2);

        assertNotEquals(a, b);
    }

    @Test
    public void equalsNull() {
        Term a = new Term(0, 1);

        assertNotEquals(a, null);
    }

    @Test
    public void equalsSelf() {
        Term a = new Term(0, 1);

        assertEquals(a, a);
    }

    @Test
    public void equalsEqualExponentAndCoef() {
        Term a = new Term(0, 1);
        Term b = new Term(0, 1);

        assertEquals(a, b);
    }


    @Test
    public void scanTermBadEmpty() {
        assertNull(Term.scanTerm(new Scanner("")));
    }

    @Test
    public void scanTermBadCoefAndExponent() {
        assertNull(Term.scanTerm(new Scanner("a b")));
    }

    @Test
    public void scanTermBadExponent() {
        assertNull(Term.scanTerm(new Scanner("0 b")));
    }

    @Test
    public void scanTermBadCoef() {
        assertNull(Term.scanTerm(new Scanner("a 0")));
    }

    @Test
    public void scanTermBadRealExponent() {
        assertNull(Term.scanTerm(new Scanner("0 0.2")));
    }

    @Test
    public void scanTermIntCoef() {
        Term a = Term.scanTerm(new Scanner("0 0"));
        Term expected = new Term(0, 0);

        assertEquals(expected, a);
    }

    @Test
    public void scanTermNegativeIntCoef() {
        Term a = Term.scanTerm(new Scanner("-1 2"));
        Term expected = new Term(-1, 2);

        assertEquals(expected, a);
    }

    @Test
    public void scanTermRealCoef() {
        Term a = Term.scanTerm(new Scanner("1.5 3"));
        Term expected = new Term(1.5, 3);

        assertEquals(expected, a);
    }

    @Test
    public void scanTermNegativeRealCoef() {
        Term a = Term.scanTerm(new Scanner("-1.5 3"));
        Term expected = new Term(-1.5, 3);

        assertEquals(expected, a);
    }
}