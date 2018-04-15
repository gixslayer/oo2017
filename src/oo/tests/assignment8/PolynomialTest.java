package oo.tests.assignment8;

import oo.assignment8.Polynomial;
import oo.assignment8.Term;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author Ciske Harsema - s1010048
 * @author Michiel Verloop - s1009995
 */
public class PolynomialTest {

    @Test
    public void fromStringDegree0() {
        List<Term> a = new Polynomial("1 0").getTerms();
        List<Term> expected = Arrays.asList(new Term(1, 0));

        assertEquals(expected, a);
    }

    @Test
    public void fromStringDegree1() {
        List<Term> a = new Polynomial("1 0 4 1").getTerms();
        List<Term> expected = Arrays.asList(new Term(1, 0), new Term(4, 1));

        assertEquals(expected, a);
    }

    @Test
    public void fromStringDegree2() {
        List<Term> a = new Polynomial("1 0 4 1 5 2").getTerms();
        List<Term> expected = Arrays.asList(new Term(1, 0), new Term(4, 1), new Term(5, 2));

        assertEquals(expected, a);
    }

    @Test
    public void fromCopy() {
        Polynomial a = new Polynomial("1 2 3 4 5 6");
        Polynomial b = new Polynomial(a);

        assertEquals(a, b);
        assertFalse(a == b);
    }

    @Test
    public void toStringEmpty() {
        Polynomial a = new Polynomial();
        String expected = "0";

        assertEquals(expected, a.toString());
    }

    @Test
    public void toStringDegree0() {
        Polynomial a = new Polynomial("4 0");
        String expected = String.format("%f", 4.0);

        assertEquals(expected, a.toString());
    }

    @Test
    public void toStringDegree1() {
        Polynomial a = new Polynomial("2 1");
        String expected = String.format("%fx", 2.0);

        assertEquals(expected, a.toString());
    }

    @Test
    public void toStringDegree2() {
        Polynomial a = new Polynomial("2 2");
        String expected = String.format("%fx^2", 2.0);

        assertEquals(expected, a.toString());
    }

    @Test
    public void toStringDegree4() {
        Polynomial a = new Polynomial("2 2 3 4");
        String expected = String.format("%fx^2 + %fx^4", 2.0, 3.0);

        assertEquals(expected, a.toString());
    }

    @Test
    public void plus() {
        Polynomial a = new Polynomial("1 1 2 2  3 3  4 4 5 5");
        Polynomial b = new Polynomial("1 1 4 2 -3 3 -5 4 3 5");
        Polynomial expected = new Polynomial("2 1 6 2 -1 4 8 5");

        a.plus(b);

        assertEquals(expected, a);
    }

    @Test
    public void minus() {
        Polynomial a = new Polynomial("4 1  6 3 6 4");
        Polynomial b = new Polynomial("3 1 -6 3 6 4");
        Polynomial expected = new Polynomial("1 1 12 3");

        a.minus(b);

        assertEquals(expected, a);
    }

    @Test
    public void minus_self() {
        Polynomial a = new Polynomial("-3 0 6 1 8 4");
        Polynomial expected = new Polynomial();

        a.minus(a);

        assertEquals(expected, a);
    }

    @Test
    public void times() {
        Polynomial a = new Polynomial("-3 1 4 2 1 3");
        Polynomial b = new Polynomial("4 1 2 2");
        Polynomial expected = new Polynomial("-12 2 10 3 12 4 2 5");

        a.times(b);

        assertEquals(expected, a);
    }

    @Test
    public void divide() {
        Polynomial a = new Polynomial("-4 1 4 2 8 3");
        Polynomial b = new Polynomial("2 1 2 2");
        Polynomial expected = new Polynomial("-2 0 4 1");

        a.divide(b);

        assertEquals(expected, a);
    }

    @Test
    public void divide_self() {
        Polynomial a = new Polynomial("9 0 -2 1 4 2");
        Polynomial expected = new Polynomial("1 0");

        a.divide(a);

        assertEquals(expected, a);
    }

    @Test
    public void plus_inverse_of_minus() {
        Polynomial a = new Polynomial("9 0 -2 1 4 2");
        Polynomial b = new Polynomial("3 0 3 1 2 3");
        Polynomial expected = new Polynomial(a);

        a.plus(b);
        a.minus(b);

        assertEquals(expected, a);
    }

    @Test
    public void minus_is_negative_add() {
        Polynomial a = new Polynomial("9 0 -2 1 4 2");
        Polynomial b = new Polynomial("3 0 3 1 2 3");
        Polynomial c = new Polynomial(a);
        Polynomial d = new Polynomial(b);

        a.minus(b);
        d.times(new Polynomial("-1 0"));
        c.plus(d);

        assertEquals(a, c);
    }

    @Test
    public void plus_is_associative() {
        Polynomial a = new Polynomial("1 2");
        Polynomial b = new Polynomial("3 4");
        Polynomial c = new Polynomial("1 2 3 4");
        Polynomial d = new Polynomial(a);
        Polynomial e = new Polynomial(b);
        Polynomial f = new Polynomial(c);

        a.plus(b);
        a.plus(c);

        e.plus(f);
        d.plus(e);

        assertEquals(a, d);
    }

    @Test
    public void plus_is_commutative() {
        Polynomial a = new Polynomial("9 0 -2 1 4 2");
        Polynomial b = new Polynomial("3 0 3 1 2 3");
        Polynomial c = new Polynomial(a);
        Polynomial d = new Polynomial(b);

        a.plus(b);
        d.plus(c);

        assertEquals(a, d);
    }

    @Test
    public void times_is_associative() {
        Polynomial a = new Polynomial("1 2");
        Polynomial b = new Polynomial("3 4");
        Polynomial c = new Polynomial("1 2 3 4");
        Polynomial d = new Polynomial(a);
        Polynomial e = new Polynomial(b);
        Polynomial f = new Polynomial(c);

        a.times(b);
        a.times(c);

        e.times(f);
        d.times(e);

        assertEquals(a, d);
    }

    @Test
    public void times_is_commutative() {
        Polynomial a = new Polynomial("9 0 -2 1 4 2");
        Polynomial b = new Polynomial("3 0 3 1 2 3");
        Polynomial c = new Polynomial(a);
        Polynomial d = new Polynomial(b);

        a.times(b);
        d.times(c);

        assertEquals(a, d);
    }

    @Test
    public void is_left_distributive() {
        Polynomial a = new Polynomial("1 2");
        Polynomial b = new Polynomial("3 4");
        Polynomial c = new Polynomial("1 2 3 4");
        Polynomial d1 = new Polynomial(a);
        Polynomial d2 = new Polynomial(a);
        Polynomial e = new Polynomial(b);
        Polynomial f = new Polynomial(c);

        b.plus(c);
        a.times(b);

        d1.times(e);
        d2.times(f);
        d1.plus(d2);

        assertEquals(a, d1);
    }

    @Test
    public void is_right_distributive() {
        Polynomial a = new Polynomial("1 2");
        Polynomial b = new Polynomial("3 4");
        Polynomial c = new Polynomial("1 2 3 4");
        Polynomial d = new Polynomial(a);
        Polynomial e = new Polynomial(b);
        Polynomial f = new Polynomial(c);

        b.plus(c);
        b.times(a);

        e.times(d);
        f.times(d);
        e.plus(f);

        assertEquals(b, e);
    }

    @Test
    public void equalsNull() {
        Polynomial a = new Polynomial("1 2");

        assertNotEquals(a, null);
    }

    @Test
    public void equalsSelf() {
        Polynomial a = new Polynomial("1 2");

        assertEquals(a, a);
    }

    @Test
    public void equalsCopy() {
        Polynomial a = new Polynomial("1 2 3 4");
        Polynomial b = new Polynomial(a);

        assertEquals(a, b);
    }

    @Test
    public void equalsDifferentExponentAndCoef() {
        Polynomial a = new Polynomial("1 2");
        Polynomial b = new Polynomial("3 4");

        assertNotEquals(a, b);
    }

    @Test
    public void equalsMoreTerms() {
        Polynomial a = new Polynomial("1 2");
        Polynomial b = new Polynomial("1 2 3 4");

        assertNotEquals(a, b);
    }

    @Test
    public void equalsDifferentExponent() {
        Polynomial a = new Polynomial("1 2 3 4");
        Polynomial b = new Polynomial("1 1 3 4");

        assertNotEquals(a, b);
    }

    @Test
    public void equalsDifferentCoef() {
        Polynomial a = new Polynomial("1 2 3 4");
        Polynomial b = new Polynomial("1 2 4 4");

        assertNotEquals(a, b);
    }
}