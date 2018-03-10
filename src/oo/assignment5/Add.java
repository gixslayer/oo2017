package oo.assignment5;

import java.util.Map;

import static oo.assignment5.ExpressionFactory.*;

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
        Expression operand1 = getLeftOperand().optimize();
        Expression operand2 = getRightOperand().optimize();
        Expression leftOperand = normalize(operand1, operand2);
        Expression rightOperand = leftOperand == operand1 ? operand2 : operand1;

        if(leftOperand instanceof Constant) {
            Constant leftConstant = ((Constant)leftOperand);

            if(leftConstant.getValue() == 0) { // add(0, x) -> x
                return rightOperand;
            } else if(rightOperand instanceof Constant) { // add(n,m) -> n+m
                return con(leftConstant.getValue() + ((Constant) rightOperand).getValue());
            } else if(rightOperand instanceof Add) { // add(n,add(m,x)) -> add(n+m,x)
                return optimizeAddWithConstant((Add)rightOperand, leftConstant);
            }
        } else if(leftOperand instanceof Variable) {
            return optimizeVariable((Variable)leftOperand, rightOperand);
        } else if(leftOperand instanceof Add) {
            if(rightOperand instanceof Add) { // add(add(n,x),add(m,y)) -> add(n+m,add(x,y))
                return optimizeNestedAdd((Add)leftOperand, (Add)rightOperand);
            } else if(rightOperand instanceof Multiply) {
                return optimizeAddMultiply((Add)leftOperand, (Multiply)rightOperand);
            }
        } else if(leftOperand instanceof Multiply) {
            if(rightOperand instanceof Multiply) {
                return optimizeNestedMultiply((Multiply)leftOperand, (Multiply)rightOperand);
            }
        }

        return new Add(leftOperand, rightOperand);
    }

    private static Expression optimizeVariable(Variable variable, Expression operand) {
        if(variable.equals(operand)) { // add(v,v) -> mul(2,v)
            return mul(con(2), variable);
        } else if(operand instanceof Multiply) { // add(v,mul(v,x)) -> mul(add(x,1),v)
            Multiply multiply = (Multiply)operand;
            Expression leftOperand = multiply.getLeftOperand();
            Expression rightOperand = multiply.getRightOperand();

            if(variable.equals(leftOperand)) {
                return mul(add(rightOperand, con(1)).optimize(), leftOperand);
            } else if(variable.equals(rightOperand)) {
                return mul(add(leftOperand, con(1)).optimize(), rightOperand);
            }
        } else if(operand instanceof Add) { // add(v,add(v,x)) -> add(mul(2,v),x)
            Add add = (Add)operand;
            Expression leftOperand = add.getLeftOperand();
            Expression rightOperand = add.getRightOperand();

            if(variable.equals(leftOperand)) {
                return add(mul(con(2), variable), rightOperand);
            } else if(variable.equals(rightOperand)) {
                return add(mul(con(2), variable), leftOperand);
            }
        }

        return new Add(variable, operand);
    }

    private static Expression optimizeAddWithConstant(Add add, Constant constant) {
        Expression leftOperand = add.getLeftOperand();
        Expression rightOperand = add.getRightOperand();

        if(leftOperand instanceof Constant) {
            return add(add(leftOperand, constant).optimize(), rightOperand);
        }

        return new Add(constant, add);
    }

    private static Expression optimizeNestedAdd(Constant constant, Expression operand, Add add) {
        Expression leftOperand = add.getLeftOperand();
        Expression rightOperand = add.getRightOperand();

        if(leftOperand instanceof Constant) {
            return add(add(leftOperand, constant).optimize(), add(rightOperand, operand).optimize());
        }

        return add(add(constant, operand), add(leftOperand, rightOperand));
    }

    private static Expression optimizeNestedAdd(Add left, Add right) {
        Expression leftOperand = left.getLeftOperand();
        Expression rightOperand = left.getRightOperand();

        if(leftOperand instanceof Constant) {
            return optimizeNestedAdd((Constant)leftOperand, rightOperand,right);
        }

        return add(left, right);
    }

    private static Expression optimizeNestedMultiply(Constant constant, Variable variable, Multiply multiply) {
        Expression leftOperand = multiply.getLeftOperand();
        Expression rightOperand = multiply.getRightOperand();

        if(leftOperand instanceof Constant && variable.equals(rightOperand)) {
            return mul(add(leftOperand, constant).optimize(), variable);
        }

        return add(mul(constant, variable), multiply);
    }

    private static Expression optimizeNestedMultiply(Multiply left, Multiply right) {
        Expression leftOperand = left.getLeftOperand();
        Expression rightOperand = left.getRightOperand();

        if(leftOperand instanceof Constant && rightOperand instanceof Variable) {
            return optimizeNestedMultiply((Constant)leftOperand, (Variable)rightOperand, right);
        }

        return add(left, right);
    }

    private static Expression optimizeAddMultiply(Variable variable, Expression operand, Multiply multiply) {
        Expression leftOperand = multiply.getLeftOperand();
        Expression rightOperand = multiply.getRightOperand();

        if(variable.equals(leftOperand) && variable.equals(rightOperand)) {
            return add(mul(add(variable, con(1)), variable), operand);
        }

        return add(add(variable, operand), multiply);
    }

    private static Expression optimizeAddMultiply(Add add, Multiply multiply) {
        Expression leftOperand = add.getLeftOperand();
        Expression rightOperand = add.getRightOperand();

        if(leftOperand instanceof Constant && rightOperand instanceof Variable) {
            Constant constant = (Constant)leftOperand;
            Variable variable = (Variable)rightOperand;
            Expression leftTerm = multiply.getLeftOperand();
            Expression rightTerm = multiply.getRightOperand();

            if(variable.equals(leftTerm)) {
                // add(add(n,v),mul(v,x)) -> add(n,mul(add(x,1),v))
                return add(constant, mul(add(rightTerm ,con(1)).optimize(), variable)).optimize();
            } else if(variable.equals(rightTerm)) {
                // add(add(n,v),mul(x,v)) -> add(n,mul(add(x,1),v))
                return add(constant, mul(add(leftTerm ,con(1)).optimize(), variable)).optimize();
            }
        } else if(leftOperand instanceof Variable) {
            return optimizeAddMultiply((Variable)leftOperand, rightOperand, multiply);
        } else if(rightOperand instanceof Variable) {
            return optimizeAddMultiply((Variable)rightOperand, leftOperand, multiply);
        }

        return add(add, multiply);
    }
}
