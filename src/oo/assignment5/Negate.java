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
        return String.format("- (%s)", getOperand());
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
        }

        return new Negate(operand);
    }
}
