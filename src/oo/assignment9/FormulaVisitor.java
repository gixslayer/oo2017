package oo.assignment9;

/**
 * @author Ciske Harsema - s1010048
 * @author Michiel Verloop - s1009995
 */
public interface FormulaVisitor<T> {
    T visit(UnaryOperator formula);
    T visit(BinaryOperator formula);
    T visit(Constant formula);
    T visit(Proposition formula);
}
