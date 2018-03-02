package oo.assignment4;

/**
 * @author Ciske Harsema - s1010048
 * @author Michiel Verloop - s1009995
 */
public interface HumanStrategyObserver {

    /**
     * Indicates the strategy is in the process of making a move.
     * @param humanStrategy The strategy that generated the request.
     * @param board The state of the board.
     */
    void moveRequested(HumanStrategy humanStrategy, Board board);
}
