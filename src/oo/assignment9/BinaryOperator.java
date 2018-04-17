package oo.assignment9;

/**
 * @author Ciske Harsema - s1010048
 * @author Michiel Verloop - s1009995
 */
public class BinaryOperator implements Formula {
    private final Formula leftOperand;
    private final Formula rightOperand;
    private final BinaryOperatorStrategy<Boolean> strategy;

    public BinaryOperator(Formula leftOperand, Formula rightOperand, BinaryOperatorStrategy<Boolean> strategy) {
        this.leftOperand = leftOperand;
        this.rightOperand = rightOperand;
        this.strategy = strategy;
    }

    public Formula getLeftOperand() {
        return leftOperand;
    }

    public Formula getRightOperand() {
        return rightOperand;
    }

    public BinaryOperatorStrategy<Boolean> getStrategy() {
        return strategy;
    }

    @Override
    public <T> T accept(FormulaVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
