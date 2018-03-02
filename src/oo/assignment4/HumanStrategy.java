package oo.assignment4;

import oo.utils.Contract;

/**
 * @author Ciske Harsema - s1010048
 * @author Michiel Verloop - s1009995
 */
public class HumanStrategy implements PlayerStrategy {
    private final HumanStrategyObserver observer;
    private Move move;

    public HumanStrategy(HumanStrategyObserver observer) {
        Contract.notNull(observer, "observer cannot be null");

        this.observer = observer;
        move = null;
    }

    /**
     * A human decides what move will be made.
     * @param board The current board.
     * @param myMark The mark of the player.
     * @return The selected move.
     */
    @Override
    public Move determineMove(Board board, Mark myMark) {
        observer.moveRequested(this, board);

        return move;
    }

    public void setMove(Move move) {
        this.move = move;
    }

    public Move getMove() {
        return move;
    }

    @Override
    public String toString() {
        return "human controlled strategy";
    }
}
