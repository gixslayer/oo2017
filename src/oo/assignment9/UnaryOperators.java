package oo.assignment9;

/**
 * @author Ciske Harsema - s1010048
 * @author Michiel Verloop - s1009995
 */
public enum UnaryOperators implements UnaryOperatorStrategy<Boolean> {
    Not("~") {
        @Override
        public Boolean apply(Boolean operand) {
            return !operand;
        }
    };

    private final String symbol;

    UnaryOperators(String symbol) {
        this.symbol = symbol;
    }

    @Override
    public String getSymbol() {
        return symbol;
    }
}
