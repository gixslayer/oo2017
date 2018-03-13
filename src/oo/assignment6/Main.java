package oo.assignment6;

/**
 * @author Ciske Harsema - s1010048
 * @author Michiel Verloop - s1009995
 */
public class Main {
    public static void main(String[] args) {
        // c0ffee does not solve (N=3).
        // c0ffee5 solves in 52 steps (N=3).
        // coffee solves in 276 steps (N=4).
        GameSolver.trySolve(0xc0ffee5);
    }
}
