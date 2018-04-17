package oo.assignment9;

/**
 * @author Ciske Harsema - s1010048
 * @author Michiel Verloop - s1009995
 */
public class PrintFormulaVisitor implements FormulaVisitor<Void> {
    private static boolean shouldParenthesize(Formula formula, Formula operand) {
        if(formula instanceof UnaryOperator)  {
            return operand instanceof BinaryOperator;
        }  else if(formula instanceof BinaryOperator && operand instanceof BinaryOperator) {
            return ((BinaryOperator) operand).getStrategy().getBindingStrength() <
                    ((BinaryOperator) formula).getStrategy().getBindingStrength();
        }

        return false;
    }

    @Override
    public Void visit(UnaryOperator formula) {
        boolean parenthesizeOperand = shouldParenthesize(formula, formula.getOperand());

        System.out.print(formula.getStrategy());
        if(parenthesizeOperand) System.out.print("(");
        formula.getOperand().accept(this);
        if(parenthesizeOperand) System.out.print(")");

        return null;
    }

    @Override
    public Void visit(BinaryOperator formula) {
        boolean parenthesizeLeftOperand = shouldParenthesize(formula, formula.getLeftOperand());
        boolean parenthesizeRightOperand = shouldParenthesize(formula, formula.getRightOperand());

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
