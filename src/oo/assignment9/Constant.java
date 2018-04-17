package oo.assignment9;

/**
 * @author Ciske Harsema - s1010048
 * @author Michiel Verloop - s1009995
 */
public enum Constant implements Formula {
    True(true) {
        @Override
        public <T> T accept(FormulaVisitor<T> visitor) {
            return visitor.visit(this);
        }
    },
    False(false) {
        @Override
        public <T> T accept(FormulaVisitor<T> visitor) {
            return visitor.visit(this);
        }
    };

    private final boolean value;

    Constant(boolean value) {
        this.value = value;
    }

    public boolean getValue() {
        return value;
    }

    public static Constant fromValue(boolean value) {
        return value ? True : False;
    }
}
