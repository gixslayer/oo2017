package oo.assignment5;

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
        String variable = scanner.next();
        String number = scanner.next();

        try {
            double value = Double.parseDouble(number);

            store.put(variable, value);

            System.out.println("[variables]");
            store.forEach((k, v) -> System.out.printf("%s = %f\n", k, v));
        } catch (NumberFormatException ex) {
            scanner.nextLine();
            System.out.printf("Could not parse %s as a double\n", number);
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
        System.out.println("exit|quit     - exit application");
        System.out.println("set VAR VALUE - set variable to value");
        System.out.println("clear VAR     - remove variable from store");
        System.out.println("expr EXPR     - evaluate expression");
        System.out.println("list          - list variable store");
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
            } else {
                scanner.nextLine();
                System.out.println("Unknown command (see help)");
            }
        } while(keepRunning);
    }
}
