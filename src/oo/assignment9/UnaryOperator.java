package oo.assignment9;

/**
 * @author Ciske Harsema - s1010048
 * @author Michiel Verloop - s1009995
 */
public class UnaryOperator implements Formula {
    private final Formula operand;
    private final UnaryOperatorStrategy<Boolean> strategy;

    public UnaryOperator(Formula operand, UnaryOperatorStrategy<Boolean> strategy) {
        this.operand = operand;
        this.strategy = strategy;
    }

    public Formula getOperand() {
        return operand;
    }

    public UnaryOperatorStrategy<Boolean> getStrategy() {
        return strategy;
    }

    @Override
    public <T> T accept(FormulaVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
