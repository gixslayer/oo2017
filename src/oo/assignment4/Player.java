package oo.assignment4;

import oo.utils.Contract;

/**
 * @author Ciske Harsema - s1010048
 * @author Michiel Verloop - s1009995
 */
public class Player {
    private final String name;
    private final Mark mark;
    private final PlayerStrategy strategy;

    public Player(String name, Mark mark, PlayerStrategy strategy) {
        Contract.notNull(name, "name cannot be null");
        Contract.notNull(mark, "mark cannot be null");
        Contract.notNull(strategy, "strategy cannot be null");

        this.name = name;
        this.mark = mark;
        this.strategy = strategy;
    }

    /**
     * Makes the next move for the given board.
     * @param board The current board.
     * @return The move made.
     */
    public Move makeMove(Board board) {
        Contract.notNull(board, "board cannot be null");

        Move move = strategy.determineMove(board, mark);

        board.makeMove(move, mark);

        return move;
    }

    public String getName() {
        return name;
    }

    public Mark getMark() {
        return mark;
    }

    @Override
    public String toString() {
        return String.format("%s, using %s", name, strategy);
    }
}
