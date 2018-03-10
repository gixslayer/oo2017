package oo.assignment5;

import java.util.Map;

/**
 * @author Ciske Harsema - s1010048
 * @author Michiel Verloop - s1009995
 */
public class Negate extends UnaryExpression {

    public Negate(Expression operand) {
        super(operand);
    }

    @Override
    public String toString() {
        Expression operand = getOperand();
        String format = "-%s";

        if(operand instanceof BinaryExpression && !(operand instanceof Add)) {
            // Add will already add brackets, no need to duplicate them.
            format = "-(%s)";
        }

        return String.format(format, operand);
    }

    @Override
    public double eval(Map<String, Double> store) {
        return -getOperand().eval(store);
    }

    @Override
    public Expression optimize() {
        Expression operand = getOperand().optimize();

        if(operand instanceof Constant) {
            return new Constant(-((Constant) operand).getValue()); // neg(n) -> -n
        } else if(operand instanceof Negate) {
            return ((Negate) operand).getOperand(); // neg(neg(x)) -> x
        }

        return new Negate(operand);
    }
}
