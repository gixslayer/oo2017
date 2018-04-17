package oo.assignment9;

/**
 * @author Ciske Harsema - s1010048
 * @author Michiel Verloop - s1009995
 */
public enum BinaryOperators implements BinaryOperatorStrategy<Boolean> {
    And(3) {
        @Override
        public Boolean apply(Boolean leftOperand, Boolean rightOperand) {
            return leftOperand && rightOperand;
        }

        @Override
        public String toString() {
            return "/\\";
        }
    },
    Or(2) {
        @Override
        public Boolean apply(Boolean leftOperand, Boolean rightOperand) {
            return  leftOperand || rightOperand;
        }

        @Override
        public String toString() {
            return "\\/";
        }
    },
    Implication(1) {
        @Override
        public Boolean apply(Boolean leftOperand, Boolean rightOperand) {
            return !leftOperand || rightOperand;
        }

        @Override
        public String toString() {
            return "->";
        }
    },
    Equivalence(0) {
        @Override
        public Boolean apply(Boolean leftOperand, Boolean rightOperand) {
            return leftOperand == rightOperand;
        }

        @Override
        public String toString() {
            return "<->";
        }
    };

    private final int bindingStrength;

    BinaryOperators(int bindingStrength) {
        this.bindingStrength = bindingStrength;
    }

    @Override
    public int getBindingStrength() {
        return bindingStrength;
    }
}
