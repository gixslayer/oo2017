package oo.assignment9;

/**
 * @author Ciske Harsema - s1010048
 * @author Michiel Verloop - s1009995
 */
public interface Formula {
    <T> T accept(FormulaVisitor<T> visitor);
}
