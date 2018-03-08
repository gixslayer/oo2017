package oo.assignment5;

import java.util.Map;

/**
 * @author Ciske Harsema - s1010048
 * @author Michiel Verloop - s1009995
 */
public class Add extends BinaryExpression {

    public Add(Expression leftOperand, Expression rightOperand) {
        super(leftOperand, rightOperand);
    }

    @Override
    public String toString() {
        return String.format("(%s + %s)", getLeftOperand(), getRightOperand());
    }

    @Override
    public double eval(Map<String, Double> store) {
        double leftOperand = getLeftOperand().eval(store);
        double rightOperand = getRightOperand().eval(store);

        return leftOperand + rightOperand;
    }

    @Override
    public Expression optimize() {
        Expression leftOperand = getLeftOperand().optimize();
        Expression rightOperand = getRightOperand().optimize();

        if(leftOperand instanceof Constant) {
            Constant leftConstant = (Constant)leftOperand;

            if(leftConstant.getValue() == 0) {
                return rightOperand; // 0 + y -> y
            } else if(rightOperand instanceof Constant) {
                Constant rightConstant = (Constant)rightOperand;

                return new Constant(leftConstant.getValue() + rightConstant.getValue()); // n + m -> n + m
            }
        } else if(rightOperand instanceof Constant) {
            Constant rightConstant = (Constant)rightOperand;

            if(rightConstant.getValue() == 0) {
                return leftOperand; // x + 0 -> x
            }
        }

        return new Add(leftOperand, rightOperand);
    }
}
