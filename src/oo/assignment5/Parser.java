package oo.assignment5;

import java.text.ParseException;
import java.util.*;

/**
 * @author Ciske Harsema - s1010048
 * @author Michiel Verloop - s1009995
 */
public class Parser {
    private final Scanner scanner;
    private final Map<String, Double> store;

    public Parser() {
        scanner = new Scanner(System.in);
        store = new HashMap<>();
    }

    private void cmdSet() {
        try {
            String variable = scanner.next();
            double value = scanner.nextDouble();

            store.put(variable, value);

            System.out.println("[variables]");
            store.forEach((k, v) -> System.out.printf("%s = %f\n", k, v));
        } catch (InputMismatchException ex) {
            scanner.nextLine();
            System.out.println("Could not parse value as a double");
        }
    }

    private void cmdClear() {
        String variable = scanner.next();

        store.remove(variable);

        System.out.println("[variables]");
        store.forEach((k, v) -> System.out.printf("%s = %f\n", k, v));
    }

    private void cmdList() {
        System.out.println("[variables]");
        store.forEach((k, v) -> System.out.printf("%s = %f\n", k, v));
    }

    private void cmdHelp() {
        System.out.println("exit|quit        - exit application");
        System.out.println("set VAR VALUE    - set variable to value");
        System.out.println("clear VAR        - remove variable from store");
        System.out.println("expr EXPR        - evaluate expression");
        System.out.println("list             - list variable store");
        System.out.println("rand DEPTH RANGE - gen depth bound random expression with range bounded constants");
    }

    private void cmdExpr() {
        try {
            Expression expression = ExpressionParser.parse(scanner.nextLine(), store);
            Expression optimized = expression.optimize();
            double expressionValue = expression.eval(store);
            double optimizedValue = optimized.eval(store);

            System.out.printf("Parsed expression:    %s\n", expression);
            System.out.printf("Optimized expression: %s\n", optimized);
            System.out.printf("Evaluation: %f (parsed) %f (optimized)\n", expressionValue, optimizedValue);
        } catch (InvalidExpressionException ex) {
            System.out.printf("Error parsing expression: %s\n", ex.getMessage());
        }
    }

    private void cmdRand() {
        try {
            int maxDepth = scanner.nextInt();
            int range = scanner.nextInt();

            Expression expression = ExpressionGenerator.generate(maxDepth, range, store.keySet());
            Expression optimized = expression.optimize();
            double expressionValue = expression.eval(store);
            double optimizedValue = optimized.eval(store);

            System.out.printf("Generated expression: %s\n", expression);
            System.out.printf("Optimized expression: %s\n", optimized);
            System.out.printf("Evaluation: %f (generated) %f (optimized)\n",
                    expressionValue, optimizedValue);
        } catch (InputMismatchException ex) {
            scanner.nextLine();
            System.out.println("Could not value as an integer");
        }
    }

    public void run() {
        boolean keepRunning = true;

        do {
            System.out.print("\n[cmd]: ");
            String cmd = scanner.next();
            System.out.println();

            if(cmd.equalsIgnoreCase("exit") || cmd.equalsIgnoreCase("quit")) {
                keepRunning = false;
            } else if(cmd.equalsIgnoreCase("set")) {
                cmdSet();
            } else if(cmd.equalsIgnoreCase("clear")) {
                cmdClear();
            } else if(cmd.equalsIgnoreCase("list")) {
                cmdList();
            } else if(cmd.equalsIgnoreCase("help")) {
                cmdHelp();
            } else if(cmd.equalsIgnoreCase("expr")) {
                cmdExpr();
            } else if(cmd.equalsIgnoreCase("rand")) {
                cmdRand();
            } else {
                scanner.nextLine();
                System.out.println("Unknown command (see help)");
            }
        } while(keepRunning);
    }
}
