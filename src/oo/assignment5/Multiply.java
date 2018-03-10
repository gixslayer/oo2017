package oo.assignment5;

import java.util.Map;

import static oo.assignment5.ExpressionFactory.add;
import static oo.assignment5.ExpressionFactory.con;
import static oo.assignment5.ExpressionFactory.mul;

/**
 * @author Ciske Harsema - s1010048
 * @author Michiel Verloop - s1009995
 */
public class Multiply extends BinaryExpression {

    public Multiply(Expression leftOperand, Expression rightOperand) {
        super(leftOperand, rightOperand);
    }

    @Override
    public String toString() {
        return String.format("%s * %s", getLeftOperand(), getRightOperand());
    }

    @Override
    public double eval(Map<String, Double> store) {
        double leftOperand = getLeftOperand().eval(store);
        double rightOperand = getRightOperand().eval(store);

        return leftOperand * rightOperand;
    }

    @Override
    public Expression optimize() {
        Expression operand1 = getLeftOperand().optimize();
        Expression operand2 = getRightOperand().optimize();
        Expression leftOperand = normalize(operand1, operand2);
        Expression rightOperand = leftOperand == operand1 ? operand2 : operand1;

        if(leftOperand instanceof Constant) {
            Constant leftConstant = (Constant)leftOperand;

            if(leftConstant.getValue() == 0) { // mul(0,x) -> 0
                return con(0);
            } else if(leftConstant.getValue() == 1) { // mul(1,x) -> x
                return rightOperand;
            }  else if(rightOperand instanceof Constant) { // mul(n,m) -> n*m
                return con(leftConstant.getValue() * ((Constant)rightOperand).getValue());
            } else if(rightOperand instanceof Multiply) { // mul(n, mul(m, x)) -> mul(n*m, x)
                return optimizeMultiplyWithConstant((Multiply)rightOperand, leftConstant);
            } else if(rightOperand instanceof Add) { // mul(n, add(x,y)) -> add(mul(n,x), mul(n,y))
                return expandAddWithConstant((Add)rightOperand, leftConstant).optimize();
            }
        } else if(leftOperand instanceof Multiply && rightOperand instanceof Multiply) {
            // mul(mul(n,x),mul(m,y)) -> mul(n*m,mul(x,y))
            return optimizeNestedMultiply((Multiply) leftOperand, (Multiply) rightOperand);
        }

        return new Multiply(leftOperand, rightOperand);
    }

    private static Expression optimizeMultiplyWithConstant(Multiply multiply, Constant constant) {
        Expression leftOperand = multiply.getLeftOperand();
        Expression rightOperand = multiply.getRightOperand();

        if(leftOperand instanceof Constant) {
            return mul(mul(constant, leftOperand).optimize(), rightOperand);
        }

        return new Multiply(constant, multiply);
    }

    private static Expression expandAddWithConstant(Add add, Constant constant) {
        Expression leftSubOperand = add.getLeftOperand();
        Expression rightSubOperand = add.getRightOperand();

        return add(mul(constant, leftSubOperand), mul(constant, rightSubOperand));
    }

    private static Expression optimizeNestedMultiply(Constant constant, Expression operand, Multiply multiply) {
        Expression leftOperand = multiply.getLeftOperand();
        Expression rightOperand = multiply.getRightOperand();

        if(leftOperand instanceof Constant) {
            return mul(mul(constant, leftOperand).optimize(), mul(operand, rightOperand).optimize());
        }

        return new Multiply(new Multiply(constant, operand), new Multiply(leftOperand, rightOperand));
    }

    private static Expression optimizeNestedMultiply(Multiply left, Multiply right) {
        Expression leftOperand = left.getLeftOperand();
        Expression rightOperand = left.getRightOperand();

        if(leftOperand instanceof Constant) {
            return optimizeNestedMultiply((Constant)leftOperand, rightOperand, right);
        }

        return new Add(left, right);
    }
}
