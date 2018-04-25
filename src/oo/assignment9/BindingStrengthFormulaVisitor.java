package oo.assignment9;

public class BindingStrengthFormulaVisitor implements FormulaVisitor<Integer> {
    @Override
    public Integer visit(UnaryOperator formula) {
        return 4;
    }

    @Override
    public Integer visit(BinaryOperator formula) {
        return formula.getStrategy().getBindingStrength();
    }

    @Override
    public Integer visit(Constant formula) {
        return 5;
    }

    @Override
    public Integer visit(Proposition formula) {
        return 5;
    }
}
