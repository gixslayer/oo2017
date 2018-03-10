package oo.assignment5;

/**
 * @author Ciske Harsema - s1010048
 * @author Michiel Verloop - s1009995
 */
public abstract class BinaryExpression extends Expression {
    private final Expression leftOperand;
    private final Expression rightOperand;

    public BinaryExpression(Expression leftOperand, Expression rightOperand) {
        this.leftOperand = leftOperand;
        this.rightOperand = rightOperand;
    }

    public Expression getLeftOperand() {
        return leftOperand;
    }

    public Expression getRightOperand() {
        return rightOperand;
    }

    protected static Expression normalize(Expression operand1, Expression operand2) {
        if(operand1 instanceof Constant || operand2 instanceof Constant) {
            return operand1 instanceof Constant ? operand1 : operand2;
        } else if(operand1 instanceof Variable || operand2 instanceof Variable) {
            return operand1 instanceof Variable ? operand1 : operand2;
        } else if(operand1 instanceof Negate || operand2 instanceof Negate) {
            return operand1 instanceof Negate ? operand1 : operand2;
        } else if(operand1 instanceof Add || operand2 instanceof Add) {
            return operand1 instanceof Add ? operand1 : operand2;
        }  else if(operand1 instanceof Multiply || operand2 instanceof Multiply) {
            return operand1 instanceof Multiply ? operand1 : operand2;
        }

        return operand1;
    }
}
