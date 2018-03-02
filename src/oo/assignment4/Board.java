package oo.assignment4;

import oo.utils.Contract;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * @author Ciske Harsema - s1010048
 * @author Michiel Verloop - s1009995
 */
public class Board {
    /**
     * The size of the board. Note that this size must be an odd number above 2.
     */
    public static final int SIZE = 3;

    private final Slot[][] slots;

    public Board() {
        slots = new Slot[SIZE][];

        for(int i = 0; i < SIZE; ++i) {
            slots[i] = new Slot[SIZE];

            Arrays.fill(slots[i], Slot.Empty);
        }
    }

    /**
     * Copy constructor.
     * @param board The board to make a copy from.
     */
    private Board(Board board) {
        Contract.notNull(board, "board cannot be null");

        slots = new Slot[SIZE][];

        for(int i = 0; i < SIZE; ++i) {
            slots[i] = Arrays.copyOf(board.slots[i], SIZE);
        }
    }

    /**
     * Creates a copy of this board.
     * @return The copy of the board.
     */
    public Board copy() {
        return new Board(this);
    }

    public Slot getSlot(int column, int row) {
        Contract.inRange(column, 0, SIZE, "invalid column index");
        Contract.inRange(row, 0, SIZE, "invalid row index");

        return slots[column][row];
    }

    public void setSlot(int column, int row, Slot slot) {
        Contract.inRange(column, 0, SIZE, "invalid column index");
        Contract.inRange(row, 0, SIZE, "invalid row index");

        slots[column][row] = slot;
    }

    public boolean isValidMove(Move move) {
        Contract.notNull(move, "move cannot be null");

        int column = move.getColumn();
        int row = move.getRow();

        return column >= 0 && column < SIZE && row >= 0 && row < SIZE && getSlot(column, row) == Slot.Empty;
    }

    public void makeMove(Move move, Mark mark) {
        Contract.isTrue(isValidMove(move), "invalid move");
        Contract.notNull(mark, "mark cannot be null");

        setSlot(move.getColumn(), move.getRow(), mark == Mark.Circle ? Slot.Circle : Slot.Cross);
    }

    public List<Move> getAvailableMoves() {
        List<Move> result = new ArrayList<>();

        for(int c = 0; c < SIZE; ++c) {
            for(int r = 0; r < SIZE; ++r) {
                if(getSlot(c, r) == Slot.Empty) {
                    result.add(new Move(c, r));
                }
            }
        }

        return result;
    }

    /**
     * Returns the mark of the winner, if one exists.
     * @return The mark of the winner if one exists, or an empty optional otherwise.
     */
    public Optional<Mark> getWinner() {
        boolean diag1 = true;
        boolean diag2 = true;

        for(int i = 0; i < SIZE; ++i) {
            boolean column = true;
            boolean row = true;

            // Check if the current diagonal slot is equal to the previous.
            if(i >= 1) {
                diag1 &= slots[i][i] == slots[i-1][i-1];
                diag2 &= slots[i][SIZE - i - 1] == slots[i-1][SIZE - i];
            }

            // Check if column/row i is equal.
            for(int j = 1; j < SIZE; ++j) {
                column &= slots[i][j] == slots[i][j-1];
                row &= slots[j][i]  == slots[j-1][i];
            }

            Slot columnSlot = slots[i][0];
            Slot rowSlot = slots[0][i];

            // If the column/row is equal, and not empty, there is a winner.
            if(column && columnSlot != Slot.Empty) {
                return Optional.of(columnSlot.getMark());
            } else if(row && rowSlot != Slot.Empty) {
                return Optional.of(rowSlot.getMark());
            }
        }

        Slot diag1Slot = slots[0][0];
        Slot diag2Slot = slots[0][SIZE - 1];

        // If either diagonal is equal, and not empty, there is a winner. No winner otherwise.
        if(diag1 && diag1Slot != Slot.Empty) {
            return Optional.of(diag1Slot.getMark());
        } else if(diag2 && diag2Slot != Slot.Empty) {
            return Optional.of(diag2Slot.getMark());
        } else {
            return Optional.empty();
        }
    }

    public boolean isFull() {
        for(Slot[] segment : slots) {
            for(Slot slot : segment) {
                if(slot == Slot.Empty) {
                    return false;
                }
            }
        }

        return true;
    }
}
