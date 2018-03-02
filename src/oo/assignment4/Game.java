package oo.assignment4;

import oo.utils.Contract;

import java.util.Optional;

/**
 * @author Ciske Harsema - s1010048
 * @author Michiel Verloop - s1009995
 */
public class Game {
    private final Board board;
    private final Player[] players;
    private int currentPlayer;

    public Game(Player player1, Player player2) {
        Contract.notNull(player1, "player1 cannot be null");
        Contract.notNull(player2, "player2 cannot be null");
        Contract.isTrue(player1.getMark() != player2.getMark(), "Players cannot have the same mark");

        board = new Board();
        players = new Player[] {player1, player2};
        currentPlayer = player1.getMark() == Mark.Cross ? 0 : 1;
    }

    public Move playTurn() {
        Move move = getCurrentPlayer().makeMove(board);

        currentPlayer = (currentPlayer + 1) % players.length;

        return move;
    }

    public boolean isGameOver() {
        return board.isFull() || board.getWinner().isPresent();
    }

    public Optional<Player> getWinner() {
        Optional<Mark> winner = board.getWinner();

        return winner.map(mark -> players[0].getMark() == mark ? players[0] : players[1]);
    }

    public Player getCurrentPlayer() {
        return players[currentPlayer];
    }

    public Board getBoard() {
        return board;
    }
}
