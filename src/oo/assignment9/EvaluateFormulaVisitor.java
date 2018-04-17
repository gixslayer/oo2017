package oo.assignment9;

import java.util.Map;

/**
 * @author Ciske Harsema - s1010048
 * @author Michiel Verloop - s1009995
 */
public class EvaluateFormulaVisitor implements FormulaVisitor<Boolean> {
    private final Map<String, Boolean> valuation;

    public EvaluateFormulaVisitor(Map<String, Boolean> valuation) {
        this.valuation = valuation;
    }

    @Override
    public Boolean visit(UnaryOperator formula) {
        Boolean operandEvaluation = formula.getOperand().accept(this);

        return formula.getStrategy().apply(operandEvaluation);
    }

    @Override
    public Boolean visit(BinaryOperator formula) {
        Boolean leftOperandEvaluation = formula.getLeftOperand().accept(this);
        Boolean rightOperandEvaluation = formula.getRightOperand().accept(this);

        return formula.getStrategy().apply(leftOperandEvaluation, rightOperandEvaluation);
    }

    @Override
    public Boolean visit(Constant formula) {
        return formula.getValue();
    }

    @Override
    public Boolean visit(Proposition formula) {
        return valuation.get(formula.getVariable());
    }
}
