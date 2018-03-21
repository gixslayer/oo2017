package oo.assignment6;

import oo.utils.Contract;

import java.util.*;

/**
 * @author Ciske Harsema - s1010048
 * @author Michiel Verloop - s1009995
 */
public class SlidingGame implements Configuration {
    public static final int N = 3;
    public static final int SIZE = N * N;
    public static final int HOLE = SIZE;

    private static final int[][] LOOKUP_TABLE;

    static {
        // Pre-computing this table seems to be a slight performance increase.
        LOOKUP_TABLE = new int[N][N];

        for(int y = 0; y < N; ++y) {
            for(int x = 0; x < N; ++x) {
                LOOKUP_TABLE[x][y] = (int)Math.pow(31, x + y * N);
            }
        }
    }

    private final Configuration parent;
    private final int[][] board;
    private int holeX, holeY;
    private final Cached<Integer> hashCode;
    private final Cached<Integer> distance;

    /**
     * Create a new game from the given elements.
     * @param elements The list of elements that determine the game state.
     */
    public SlidingGame(List<Integer> elements) {
        Contract.notNull(elements, "elements cannot be null");
        Contract.equal(elements.size(), SIZE, "invalid elements size");

        this.parent = null;
        this.board = new int[N][N];
        this.hashCode = new Cached<>();
        this.distance = new Cached<>();

        for(int i = 0; i < SIZE; ++i) {
            int x = i % N;
            int y = i / N;
            int element = elements.get(i);

            board[x][y] = element;

            if(element == HOLE) {
                holeX = x;
                holeY = y;
            }
        }
    }

    /**
     * Copy constructor.
     * @param parent Instance to return a copy of.
     */
    private SlidingGame(SlidingGame parent) {
        this.parent = parent;
        this.board = new int[N][N];
        this.holeX = parent.holeX;
        this.holeY = parent.holeY;
        this.hashCode = parent.hashCode.copy();
        this.distance = parent.distance.copy();

        for(int y = 0; y < N; ++y) {
            for(int x = 0; x < N; ++x) {
                this.board[x][y] = parent.board[x][y];
            }
        }
    }

    /**
     * Used to compare elements in a TreeSet context.
     * @param a The first element.
     * @param b The second element.
     * @return 0 if and only if a equals b, otherwise the sign of the return value determines
     * the relative order.
     */
    public static int compareTree(SlidingGame a, SlidingGame b) {
        for(int y = 0; y < N; ++y) {
            for(int x = 0; x < N; ++x) {
                int dif = a.board[x][y] - b.board[x][y];

                if(dif != 0) {
                    return dif;
                }
            }
        }

        return 0;
    }

    /**
     * Creates a copy of the game where the specified element is moved into the empty position.
     * @param x The x coordinate of the element.
     * @param y The y coordinate of the element.
     * @return The new game state.
     */
    private SlidingGame move(int x, int y) {
        SlidingGame game = new SlidingGame(this);

        // Move element at x, y into the hole.
        game.board[holeX][holeY] = game.board[x][y];
        game.board[x][y] = HOLE;
        game.holeX = x;
        game.holeY = y;

        // Invalidate cashed hash code and distance, as a mutation just occurred.
        game.hashCode.invalidate();
        game.distance.invalidate();

        return game;
    }

    private int getManhattanDistance() {
        if(!distance.isValid()) {
            int value = 0;

            // Compute distance as sum of manhattan distance for each element.
            for(int y = 0; y < N; ++y) {
                for(int x = 0; x < N; ++x) {
                    int element = board[x][y];
                    int targetX = (element - 1) % N;
                    int targetY = (element - 1) / N;

                    value += Math.abs(targetX - x);
                    value += Math.abs(targetY - y);
                }
            }

            distance.set(value);
        }

        return distance.getValue();
    }

    public int get(int x, int y) {
        Contract.inRange(x, 0, N, "invalid x coordinate");
        Contract.inRange(y, 0, N, "invalid y coordinate");

        return board[x][y];
    }

    @Override
    public String toString() {
        return String.format("%dx%d puzzle, hole at %d, %d", N, N, holeX, holeY);
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof SlidingGame) {
            SlidingGame other = (SlidingGame)o;

            // This method is performance critical, so try fast path early out on inequality.
            if(holeX != other.holeX || holeY != other.holeY) {
                return false;
            }

            // Most of the time these hash codes are cached, thus making this a cheap comparison.
            if(hashCode() != other.hashCode()) {
                return false;
            }

            // Slow and expensive compare should only run if needed.
            for(int y = 0; y < N; ++y) {
                for(int x = 0; x < N; ++x) {
                    if(board[x][y] != other.board[x][y]) {
                        return false;
                    }
                }
            }

            return true;
        } else {
            return false;
        }
    }

    @Override
    public Configuration getParent() {
        return parent;
    }

    @Override
    public Collection<Configuration> getSuccessors() {
        Collection<Configuration> successors = new ArrayList<>();

        if(holeX > 0) { // Swap with west element.
            successors.add(move(holeX - 1, holeY));
        }

        if(holeX < N - 1) { // Swap with east element.
            successors.add(move(holeX + 1, holeY));
        }

        if(holeY > 0) { // Swap with north element.
            successors.add(move(holeX, holeY - 1));
        }

        if(holeY < N - 1) { // Swap with south element.
            successors.add(move(holeX, holeY + 1));
        }

        return successors;
    }

    @Override
    public boolean isSolution() {
        for(int i = 0; i < SIZE; ++i) {
            int x = i % N;
            int y = i / N;

            if(board[x][y] != i + 1) {
                return false;
            }
        }

        return true;
    }

    @Override
    public List<Configuration> getPathFromRoot() {
        List<Configuration> path = new ArrayList<>();

        for(Configuration node = this; node != null; node = node.getParent()) {
            path.add(node);
        }

        // Since we just constructed a path from the leaf to the root, reverse the elements.
        Collections.reverse(path);

        return path;
    }

    @Override
    public int compareTo(Configuration configuration) {
        int distance = getManhattanDistance();
        int otherDistance = Integer.MAX_VALUE;

        if(configuration instanceof SlidingGame) {
            otherDistance = ((SlidingGame)configuration).getManhattanDistance();
        }

        return distance - otherDistance;
    }

    @Override
    public int hashCode() {
        if(!hashCode.isValid()) {
            int value = 0;

            for(int y = 0; y < N; ++y) {
                for(int x = 0; x < N; ++x) {
                    //value += board[x][y] * (int)Math.pow(31, x + y * N);
                    value += board[x][y] * LOOKUP_TABLE[x][y];
                }
            }

            hashCode.set(value);
        }

        return hashCode.getValue();
    }
}
