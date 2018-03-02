package oo.assignment4;

import java.util.List;
import java.util.Random;

/**
 * @author Ciske Harsema - s1010048
 * @author Michiel Verloop - s1009995
 *
 * Random strategy, where a move is randomly selected from the list of available moves.
 */
public class RandomStrategy implements PlayerStrategy {
    private final Random random;

    public RandomStrategy() {
        random = new Random();
    }

    /**
     * Randomly select a move from the list of available moves.
     * @param board The current board.
     * @param myMark The mark of the player.
     * @return The selected move.
     */
    @Override
    public Move determineMove(Board board, Mark myMark) {
        List<Move> moves = board.getAvailableMoves();

        return moves.get(random.nextInt(moves.size()));
    }

    @Override
    public String toString() {
        return "random strategy";
    }
}
