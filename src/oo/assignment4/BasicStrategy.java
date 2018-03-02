package oo.assignment4;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

/**
 * @author Ciske Harsema - s1010048
 * @author Michiel Verloop - s1009995
 *
 * Basic rule based strategy that tries to win, or otherwise prevent a loss. As it only looks at the
 * current board, it is certainly not optimal.
 */
public class BasicStrategy implements PlayerStrategy {
    private final Random random;

    public BasicStrategy() {
        random = new Random();
    }

    /**
     * First tries to select a winning move, if one exists. If no winning move exists
     * it will try to block a winning move for the opponent. If multiple winning/blocking moves
     * exist, one will randomly be chosen. If there are no winning or blocking moves, a move is
     * randomly chosen from the possible moves.
     * @param board The current board.
     * @param myMark The mark of the player.
     * @return The selected move.
     */
    @Override
    public Move determineMove(Board board, Mark myMark) {
        Mark otherMark = myMark.other();

        List<Move> winningMoves = new ArrayList<>();
        List<Move> blockingMoves = new ArrayList<>();
        List<Move> otherMoves = new ArrayList<>();

        for(Move move : board.getAvailableMoves()) {
            Board b1 = board.copy();
            Board b2 = board.copy();

            b1.makeMove(move, myMark);
            b2.makeMove(move, otherMark);

            Optional<Mark> w1 = b1.getWinner();
            Optional<Mark> w2 = b2.getWinner();

            w1.ifPresent(e -> winningMoves.add(move));
            w2.ifPresent(e -> blockingMoves.add(move));

            if(!w1.isPresent() && !w2.isPresent()) {
                otherMoves.add(move);
            }
        }

        if(!winningMoves.isEmpty()) {
            return winningMoves.get(random.nextInt(winningMoves.size()));
        } else if(!blockingMoves.isEmpty()) {
            return blockingMoves.get(random.nextInt(blockingMoves.size()));
        } else {
            return otherMoves.get(random.nextInt(otherMoves.size()));
        }
    }

    @Override
    public String toString() {
        return "basic strategy";
    }
}
