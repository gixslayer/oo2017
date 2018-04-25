package oo.assignment9;

/**
 * @author Ciske Harsema - s1010048
 * @author Michiel Verloop - s1009995
 */
public class PrintFormulaVisitor implements FormulaVisitor<Void> {
    private static final BindingStrengthFormulaVisitor STRENGTH_VISITOR = new BindingStrengthFormulaVisitor();

    @Override
    public Void visit(UnaryOperator formula) {
        boolean parenthesizeOperand = formula.getOperand().accept(STRENGTH_VISITOR) < formula.accept(STRENGTH_VISITOR);

        System.out.print(formula.getStrategy());
        if(parenthesizeOperand) System.out.print("(");
        formula.getOperand().accept(this);
        if(parenthesizeOperand) System.out.print(")");

        return null;
    }

    @Override
    public Void visit(BinaryOperator formula) {
        boolean parenthesizeLeftOperand = formula.getLeftOperand().accept(STRENGTH_VISITOR) < formula.accept(STRENGTH_VISITOR);
        boolean parenthesizeRightOperand = formula.getRightOperand().accept(STRENGTH_VISITOR) < formula.accept(STRENGTH_VISITOR);

        if(parenthesizeLeftOperand) System.out.print("(");
        formula.getLeftOperand().accept(this);
        if(parenthesizeLeftOperand) System.out.print(")");
        System.out.printf(" %s ", formula.getStrategy());
        if(parenthesizeRightOperand) System.out.print("(");
        formula.getRightOperand().accept(this);
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
