package oo.assignment5;

/**
 * @author Ciske Harsema - s1010048
 * @author Michiel Verloop - s1009995
 */
public class ExpressionFactory {
    public static Constant con(double value) {
        return new Constant(value);
    }

    public static Variable var(String name) {
        return new Variable(name);
    }

    public static Negate neg(Expression operand) {
        return new Negate(operand);
    }

    public static Add add(Expression leftOperand, Expression rightOperand) {
        return new Add(leftOperand, rightOperand);
    }

    public static Multiply mul(Expression leftOperand, Expression rightOperand) {
        return new Multiply(leftOperand, rightOperand);
    }
}
