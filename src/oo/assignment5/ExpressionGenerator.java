package oo.assignment5;

import java.util.*;

/**
 * @author Ciske Harsema - s1010048
 * @author Michiel Verloop - s1009995
 */
public class ExpressionGenerator {
    private static final int CONSTANT_BIAS = 66;
    private static final int BINARY_BIAS = 66;
    private static final int UNARY_BIAS = 80;

    private final int constantRange;
    private final List<String> variables;
    private final Random random;

    private ExpressionGenerator(int constantRange, Collection<? extends String> variables) {
        this.constantRange = constantRange;
        this.variables = new ArrayList<>(variables);
        this.random = new Random();
    }

    private double getRandomConstant() {
        return random.nextInt(constantRange +1);
    }

    private String getRandomVariable() {
        return variables.get(random.nextInt(variables.size()));
    }

    private Expression getNullaryExpression() {
        if(random.nextInt(100) < CONSTANT_BIAS || variables.isEmpty()) {
            return new Constant(getRandomConstant());
        } else {
            return new Variable(getRandomVariable());
        }
    }

    private Expression getUnaryExpression(Expression operand) {
        return new Negate(operand);
    }

    private Expression getBinaryExpression(Expression leftOperand, Expression rightOperand) {
        switch (random.nextInt(2)) {
            case 0:
                return new Add(leftOperand, rightOperand);
            //case 1:
            default:
                return new Multiply(leftOperand, rightOperand);
        }
    }

    private Expression generate(int maxDepth) {
        int roll = random.nextInt(100);

        if(maxDepth == 1) {
            return getNullaryExpression();
        } else if(roll < BINARY_BIAS) {
            return getBinaryExpression(generate(maxDepth - 1), generate(maxDepth - 1));
        } else if(roll < UNARY_BIAS) {
            return getUnaryExpression(generate(maxDepth - 1));
        } else {
            return getNullaryExpression();
        }
    }

    public static Expression generate(int maxDepth, int constantRange, Collection<? extends String> variables) {
        return new ExpressionGenerator(constantRange, variables).generate(maxDepth);
    }
}
