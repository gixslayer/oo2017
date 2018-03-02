package oo.assignment4;

import oo.utils.Contract;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * @author Ciske Harsema - s1010048
 * @author Michiel Verloop - s1009995
 */
public class HumanStrategyTUIController implements HumanStrategyObserver {
    private final Scanner scanner;

    public HumanStrategyTUIController(Scanner scanner) {
        Contract.notNull(scanner, "scanner cannot be null");

        this.scanner = scanner;
    }

    /**
     * Invoked when the user has to select a move.
     * @param humanStrategy The strategy that generated the request.
     * @param board The state of the board.
     */
    @Override
    public void moveRequested(HumanStrategy humanStrategy, Board board) {
        Move move;

        do {
            move = getMoveFromUser();

            if(!board.isValidMove(move)) {
                System.out.println("That is not a valid move");
            }
        } while(!board.isValidMove(move));

        humanStrategy.setMove(move);
    }

    /**
     * Gets the move from the user, which might not be valid.
     * @return The move the user entered.
     */
    private Move getMoveFromUser() {
        System.out.print("Enter move as 'column row' (0 0 is top left): ");

        try {
            int column = scanner.nextInt();
            int row = scanner.nextInt();
            scanner.nextLine();

            return new Move(column, row);
        } catch(InputMismatchException ex) {
            scanner.nextLine();
            return new Move(-1, -1);
        }
    }
}
