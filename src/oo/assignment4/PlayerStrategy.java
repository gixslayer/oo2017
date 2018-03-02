package oo.assignment4;

/**
 * @author Ciske Harsema - s1010048
 * @author Michiel Verloop - s1009995
 */
public interface PlayerStrategy {

    /**
     * Determines the move to be made based on the strategy.
     * @param board The current board.
     * @param myMark The mark of the player.
     * @return The selected move.
     */
    Move determineMove(Board board, Mark myMark);
}
