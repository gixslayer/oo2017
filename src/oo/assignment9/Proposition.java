package oo.assignment9;

/**
 * @author Ciske Harsema - s1010048
 * @author Michiel Verloop - s1009995
 */
public class Proposition implements Formula {
    private final String variable;

    public Proposition(String variable) {
        this.variable = variable;
    }

    public String getVariable() {
        return variable;
    }

    @Override
    public <T> T accept(FormulaVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
