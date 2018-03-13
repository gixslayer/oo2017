package oo.assignment6;

import java.util.*;

/**
 * @author Ciske Harsema - s1010048
 * @author Michiel Verloop - s1009995
 */
public class GameSolver {

    private static List<Integer> getRandomPuzzle(long seed) {
        Random random = new Random(seed);
        List<Integer> puzzle = new ArrayList<>(SlidingGame.SIZE);

        for(int i = 0; i < SlidingGame.SIZE; ++i) {
            puzzle.add(i + 1);
        }

        Collections.shuffle(puzzle, random);

        return puzzle;
    }

    private static void displayPuzzle(SlidingGame game) {
        for(int y = 0; y < SlidingGame.N; ++y) {
            for(int x = 0; x < SlidingGame.N; ++x) {
                int value = game.get(x, y);

                if(value == SlidingGame.HOLE) {
                    System.out.print("   ");
                } else {
                    System.out.printf("%2d ", value);
                }
            }

            System.out.println();
        }
    }

    /**
     * Tries to solve a random puzzle and displays the resulting solution path if one exists.
     * @param seed The seed used to generate the random puzzle.
     */
    public static void trySolve(long seed) {
        List<Integer> puzzle = getRandomPuzzle(seed);
        SlidingGame game = new SlidingGame(puzzle);
        Solver solver = new Solver(game);

        System.out.printf("Trying to solve %dx%d puzzle with seed 0x%x\n\n",
                SlidingGame.N, SlidingGame.N, seed);

        long startTime = System.nanoTime();
        Optional<Configuration> result = solver.solve();
        long endTime = System.nanoTime();
        long msDiff = (endTime - startTime) / 1000000;

        if(result.isPresent()) {
            List<Configuration> path = result.get().getPathFromRoot();

            for(Configuration configuration : path) {
                displayPuzzle((SlidingGame)configuration);
                System.out.println();
            }

            System.out.printf("Solved! took %d steps\n", path.size() -1);
        } else {
            System.out.println("Could not find solution");
        }

        System.out.printf("Done in %d ms\n", msDiff);
    }
}
