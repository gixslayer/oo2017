package oo.assignment9;

/**
 * @author Ciske Harsema - s1010048
 * @author Michiel Verloop - s1009995
 */
public class PrintFormulaVisitor implements FormulaVisitor<Void> {
    private static final BindingStrengthFormulaVisitor STRENGTH_VISITOR = new BindingStrengthFormulaVisitor();

    @Override
    public Void visit(UnaryOperator formula) {
        Formula operand = formula.getOperand();
        boolean parenthesizeOperand = operand.accept(STRENGTH_VISITOR) < formula.accept(STRENGTH_VISITOR);

        System.out.print(formula.getStrategy().getSymbol());
        if(parenthesizeOperand) System.out.print("(");
        operand.accept(this);
        if(parenthesizeOperand) System.out.print(")");

        return null;
    }

    @Override
    public Void visit(BinaryOperator formula) {
        Formula leftOperand = formula.getLeftOperand();
        Formula rightOperand = formula.getRightOperand();
        boolean parenthesizeLeftOperand = leftOperand.accept(STRENGTH_VISITOR) < formula.accept(STRENGTH_VISITOR);
        boolean parenthesizeRightOperand = rightOperand.accept(STRENGTH_VISITOR) < formula.accept(STRENGTH_VISITOR);

        if(parenthesizeLeftOperand) System.out.print("(");
        leftOperand.accept(this);
        if(parenthesizeLeftOperand) System.out.print(")");
        System.out.printf(" %s ", formula.getStrategy().getSymbol());
        if(parenthesizeRightOperand) System.out.print("(");
        rightOperand.accept(this);
        if(parenthesizeRightOperand) System.out.print(")");

        return null;
    }

    @Override
    public Void visit(Constant formula) {
        System.out.print(formula);

        return null;
    }

    @Override
    public Void visit(Proposition formula) {
        System.out.print(formula.getVariable());

        return null;
    }
}
