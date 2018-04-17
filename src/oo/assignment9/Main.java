package oo.assignment9;

import java.util.HashMap;
import java.util.Map;

import static oo.assignment9.FormulaFactory.*;

/**
 * @author Ciske Harsema - s1010048
 * @author Michiel Verloop - s1009995
 */
public class Main {

    private static void evaluate(Formula formula, boolean x, boolean y) {
        Map<String, Boolean> valuation = new HashMap<>();
        valuation.put("x", x);
        valuation.put("y", y);
        EvaluateFormulaVisitor evaluateFormulaVisitor = new EvaluateFormulaVisitor(valuation);

        System.out.println("\n[Valuation]");
        System.out.printf("x=%s\n", x);
        System.out.printf("y=%s\n", y);
        System.out.printf("eval: %s\n", formula.accept(evaluateFormulaVisitor));
    }

    public static void main(String[] args) {
        Formula formula = and(implication(proposition("x"), proposition("y")),
                or(equivalence(proposition("x"), not(proposition("y"))),
                        proposition("x")));
        Formula formula2 = and(and(not(proposition("x")), not(or(proposition("y"),
                proposition("z")))), proposition("w"));
        Formula formula3 = and(or(not(proposition("x")), not(or(proposition("y"),
                proposition("z")))), proposition("w"));
        Formula formula4 = or(and(not(proposition("x")), not(or(proposition("y"),
                proposition("z")))), proposition("w"));
        Formula formula5 = or(or(not(proposition("x")), not(or(proposition("y"),
                proposition("z")))), proposition("w"));
        PrintFormulaVisitor printFormulaVisitor = new PrintFormulaVisitor();

        System.out.print("Formula: ");
        formula.accept(printFormulaVisitor);
        System.out.println();

        evaluate(formula, false, false);
        evaluate(formula, false, true);
        evaluate(formula, true, false);
        evaluate(formula, true, true);

        System.out.print("\nFormula2: ");
        formula2.accept(printFormulaVisitor);
        System.out.print("\nFormula3: ");
        formula3.accept(printFormulaVisitor);
        System.out.print("\nFormula4: ");
        formula4.accept(printFormulaVisitor);
        System.out.print("\nFormula5: ");
        formula5.accept(printFormulaVisitor);
    }
}
