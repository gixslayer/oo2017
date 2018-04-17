package oo.assignment9;

/**
 * @author Ciske Harsema - s1010048
 * @author Michiel Verloop - s1009995
 */
public enum UnaryOperators implements UnaryOperatorStrategy<Boolean> {
    Not {
        @Override
        public Boolean apply(Boolean operand) {
            return !operand;
        }

        @Override
        public String toString() {
            return "~";
        }
    }
}
