package oo.assignment2;

import java.util.Scanner;

/**
 * @author Ciske Harsema - s1010048
 */
public class GallowsTUI {
    private final Scanner scanner;
    private Gallows gallows;

    public GallowsTUI() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Start a new game, with either a chosen or random word.
     */
    private void initGame() {
        System.out.print("Enter word to guess (leave empty for random word: ");
        String word = scanner.nextLine();

        gallows = word.isEmpty() ? new Gallows() : new Gallows(word);
    }

    /**
     * Play the game, until it is finished.
     */
    private void playGame() {
        while(!gallows.isGameOver()) {
            System.out.printf("Hint: %s\n", gallows.getHint());
            System.out.print("Guess a letter: ");

            char letter = scanner.next().charAt(0);
            boolean correct = gallows.guess(letter);

            System.out.printf("\nGuess was %s\n", correct ? "correct" : "incorrect");
            System.out.printf("Attempts: %d, mistakes: %d/%d\n\n",
                    gallows.getAttempts(), gallows.getMistakes(), Gallows.MAX_MISTAKES);
        }
    }

    /**
     * Display the outcome of the game.
     */
    private void postGame() {
        System.out.printf("You %s! (%d attempts, %d mistakes)\n",
                gallows.isGameWon() ? "won": "lost", gallows.getAttempts(),
                gallows.getMistakes());
        System.out.printf("The word was '%s'\n", gallows.getWord());
    }

    /**
     * Run a single gallows game.
     */
    public void run() {
        initGame();
        playGame();
        postGame();
    }
}
