package oo.assignment5;

import java.util.HashMap;
import java.util.Map;

import static oo.assignment5.ExpressionFactory.*;

/**
 * @author Ciske Harsema - s1010048
 * @author Michiel Verloop - s1009995
 */
public class Tests {
    private final Map<String, Double> store;

    public Tests() {
        store = new HashMap<>();
        store.put("x", 4.0);
        store.put("y", 5.0);
    }

    private void test(Expression expression) {
        Expression optimized = expression.optimize();
        double expressionValue = expression.eval(store);
        double optimizedValue = optimized.eval(store);

        System.out.printf("%s -> %s\n", expression, optimized);
        System.out.printf("expression = %f\n", expressionValue);
        System.out.printf("optimized  = %f\n\n", optimizedValue);

        assert(expressionValue == optimizedValue);
    }

    public void run() {
        System.out.println("[Variables]");
        store.forEach((n,v) -> System.out.printf("%s = %f\n", n, v));

        System.out.println("\n[Expressions]");
        test(add(mul(con(2), con(3)), var("x")));
        test(mul(add(con(3), mul(con(2), con(2))), var("x")));
        test(add(con(2), con(3)));
        test(add(var("x"), con(0)));
        test(add(con(0), var("y")));
        test(mul(con(3), con(4)));
        test(mul(con(0), var("y")));
        test(mul(con(1), var("y")));
        test(mul(var("x"), con(0)));
        test(mul(var("x"), con(1)));
        test(neg(con(8)));
        test(neg(con(-8)));
    }
}
