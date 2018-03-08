package oo.assignment5;

/**
 * @author Ciske Harsema - s1010048
 * @author Michiel Verloop - s1009995
 */
public abstract class UnaryExpression extends Expression {
    private final Expression operand;

    public UnaryExpression(Expression operand) {
        this.operand = operand;
    }

    public Expression getOperand() {
        return operand;
    }
}
