package oo.assignment9;

/**
 * @author Ciske Harsema - s1010048
 * @author Michiel Verloop - s1009995
 */
public enum BinaryOperators implements BinaryOperatorStrategy<Boolean> {
    And(3, "/\\") {
        @Override
        public Boolean apply(Boolean leftOperand, Boolean rightOperand) {
            return leftOperand && rightOperand;
        }
    },
    Or(2, "\\/") {
        @Override
        public Boolean apply(Boolean leftOperand, Boolean rightOperand) {
            return  leftOperand || rightOperand;
        }
    },
    Implication(1, "->") {
        @Override
        public Boolean apply(Boolean leftOperand, Boolean rightOperand) {
            return !leftOperand || rightOperand;
        }
    },
    Equivalence(0, "<->") {
        @Override
        public Boolean apply(Boolean leftOperand, Boolean rightOperand) {
            return leftOperand == rightOperand;
        }
    };

    private final int bindingStrength;
    private final String symbol;

    BinaryOperators(int bindingStrength, String symbol) {
        this.bindingStrength = bindingStrength;
        this.symbol = symbol;
    }

    @Override
    public int getBindingStrength() {
        return bindingStrength;
    }

    @Override
    public String getSymbol() {
        return symbol;
    }
}
