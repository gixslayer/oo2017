package oo.assignment4;

import java.util.Collections;
import java.util.Optional;
import java.util.Scanner;

/**
 * @author Ciske Harsema - s1010048
 * @author Michiel Verloop - s1009995
 */
public class TicTacToeTUI {
    private final Scanner scanner;
    private final String rowSeparator;
    private Game game;

    public TicTacToeTUI() {
        scanner = new Scanner(System.in);
        rowSeparator = String.join("", Collections.nCopies(Board.SIZE * 2 - 1, "-"));
    }

    /**
     * Runs a single game from start to completion.
     */
    public void start() {
        initializeGame();

        while(!game.isGameOver()) {
            Player currentPlayer = game.getCurrentPlayer();

            reportGameState();
            reportCurrentPlayer(currentPlayer);
            reportMove(currentPlayer, game.playTurn());
        }

        reportGameState();
        reportGameResult();
    }

    private void initializeGame() {
        String humanName = getPlayerName();
        String botName = "HAL9000";
        Mark humanMark = getPlayerMark();
        Mark botMark = humanMark.other();
        PlayerStrategy humanStrategy = new HumanStrategy(new HumanStrategyTUIController(scanner));
        PlayerStrategy botStrategy = getPlayerStrategy();
        Player humanPlayer = new Player(humanName, humanMark, humanStrategy);
        Player botPlayer = new Player(botName, botMark, botStrategy);

        game = new Game(humanPlayer, botPlayer);
    }

    private String getPlayerName() {
        String name;

        do {
            System.out.print("Enter your name: ");

            name = scanner.nextLine();

            if(name.isEmpty()) {
                System.out.println("Name cannot be empty");
            }
        } while(name.isEmpty());

        return name;
    }

    private Mark getPlayerMark() {
        Mark mark = null;

        do {
            System.out.print("Select your mark (x or o): ");

            String input = scanner.next();

            if(input.equalsIgnoreCase("x")) {
                mark = Mark.Cross;
            } else if(input.equalsIgnoreCase("o")) {
                mark = Mark.Circle;
            } else {
                System.out.println("That is not a valid mark");
            }
        } while(mark == null);

        return mark;
    }

    private PlayerStrategy getPlayerStrategy() {
        PlayerStrategy strategy = null;

        do {
            System.out.print("Select bot strategy ('r' for random, or 'b' for basic): ");

            String input = scanner.next();

            if(input.equalsIgnoreCase("r")) {
                strategy = new RandomStrategy();
            } else if(input.equalsIgnoreCase("b")) {
                strategy = new BasicStrategy();
            } else {
                System.out.println("That is not a valid strategy");
            }
        } while(strategy == null);

        return strategy;
    }

    private void reportGameState() {
        System.out.println();
        for(int r = 0; r < Board.SIZE; ++r) {
            if(r != 0) {
                System.out.println(rowSeparator);
            }

            for(int c = 0; c < Board.SIZE; ++c) {
                System.out.printf(c == 0 ? "%s" : "|%s", game.getBoard().getSlot(c, r));
            }

            System.out.println();
        }
        System.out.println();
    }

    private void reportCurrentPlayer(Player player) {
        System.out.printf("%s, it is your turn\n", player.getName());
    }

    private void reportMove(Player player, Move move) {
        System.out.printf("%s has placed a mark at %d, %d\n", player.getName(), move.getColumn(), move.getRow());
    }

    private void reportGameResult() {
        Optional<Player> winner = game.getWinner();

        if(winner.isPresent()) {
            System.out.printf("%s has won the game!\n", winner.get().getName());
        } else {
            System.out.println("The game ended in a draw");
        }
    }
}
