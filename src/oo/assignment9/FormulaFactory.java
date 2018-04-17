package oo.assignment9;

/**
 * @author Ciske Harsema - s1010048
 * @author Michiel Verloop - s1009995
 */
public class FormulaFactory {

    public static Formula proposition(String variable) {
        return new Proposition(variable);
    }

    public static Formula not(Formula operand) {
        return new UnaryOperator(operand, UnaryOperators.Not);
    }

    public static Formula and(Formula leftOperand, Formula rightOperand) {
        return new BinaryOperator(leftOperand, rightOperand, BinaryOperators.And);
    }

    public static Formula or(Formula leftOperand, Formula rightOperand) {
        return new BinaryOperator(leftOperand, rightOperand, BinaryOperators.Or);
    }

    public static Formula equivalence(Formula leftOperand, Formula rightOperand) {
        return new BinaryOperator(leftOperand, rightOperand, BinaryOperators.Equivalence);
    }

    public static Formula implication(Formula leftOperand, Formula rightOperand) {
        return new BinaryOperator(leftOperand, rightOperand, BinaryOperators.Implication);
    }
}
