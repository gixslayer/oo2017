package oo.assignment5;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Ciske Harsema - s1010048
 * @author Michiel Verloop - s1009995
 */
public class ExpressionParser {
    private final List<String> tokens;
    private final Map<Integer, Integer> brackets;
    private final Map<String, Double> store;

    private ExpressionParser(List<String> tokens, Map<Integer, Integer> brackets, Map<String, Double> store) {
        this.tokens = tokens;
        this.brackets = brackets;
        this.store = store;
    }

    private int getBinarySplitPoint(int start, int end) {
        for(int i = start; i < end; ++i) {
            String token = tokens.get(i);

            if(token.equals(",")) {
                return i;
            } else if(token.equals("(")) {
                i = brackets.get(i);
            }
        }

        throw new InvalidExpressionException("Could not find comma for binary operator");
    }

    private Expression parse(int start, int end) {
        String token = tokens.get(start);

        if(token.equals("(")) {
            return parse(start + 1, brackets.get(start) - 1);
        } else if(token.equalsIgnoreCase("add")) {
            int split = getBinarySplitPoint(start + 2, brackets.get(start + 1));

            return new Add(parse(start + 2, split), parse(split + 1, brackets.get(start + 1)));
        } else if(token.equalsIgnoreCase("mul")) {
            int split = getBinarySplitPoint(start + 2, brackets.get(start + 1));

            return new Multiply(parse(start + 2, split), parse(split + 1, brackets.get(start + 1)));
        } else if(token.equalsIgnoreCase("neg")) {
            return new Negate(parse(start + 1, brackets.get(start + 1)));
        } else {
            try {
                return new Constant(Double.parseDouble(token));
            } catch (NumberFormatException ex) {
                if(store.containsKey(token)) {
                    return new Variable(token);
                } else {
                    throw new InvalidExpressionException("Unknown token '%s'", token);
                }
            }
        }
    }

    private static Map<Integer, Integer> matchBrackets(List<String> tokens) {
        Map<Integer, Integer> result = new HashMap<>();
        Stack<Integer> stack = new Stack<>();

        for(int i = 0; i < tokens.size(); ++i) {
            String token = tokens.get(i);

            if(token.equals("(")) {
                stack.push(i);
            } else if(token.equals(")")) {
                if(stack.isEmpty()) {
                    throw new InvalidExpressionException("Closing bracket without matching opening bracket for %d-th token", i);
                }

                Integer start = stack.pop();

                result.put(start, i);
            }
        }

        if(!stack.empty()) {
            throw new InvalidExpressionException("Opening bracket without matching closing bracket for %d-th token", stack.pop());
        }

        return result;
    }

    public static Expression parse(String str, Map<String, Double> store) {
        List<String> tokens = Arrays.stream(str.split("\\s+|(?=\\()|(?=\\))|(?=,)|(?<=\\()|(?<=\\)|(?<=,))"))
                .filter(s -> !s.isEmpty()).collect(Collectors.toList());
        Map<Integer, Integer> brackets = matchBrackets(tokens);

        return new ExpressionParser(tokens, brackets, store).parse(0, tokens.size());
    }
}
