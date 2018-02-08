package oo.assignment2;

import java.util.Collections;

/**
 * @author Ciske Harsema - s1010048
 */
public class Gallows {
    public static final int MAX_MISTAKES = 10;

    private final String word;
    private final StringBuilder hint;
    private int mistakes;
    private int attempts;

    public Gallows() {
        this(Main.WORD_READER.getWord());
    }

    public Gallows(String word) {
        this.word = word;
        this.hint = new StringBuilder(String.join("",
                Collections.nCopies(word.length(), ".")));
        this.mistakes = 0;
        this.attempts = 0;
    }

    /**
     * Guess a letter of the word.
     * @param letter The letter to guess
     * @return True if guess was correct, false otherwise
     */
    public boolean guess(char letter) {
        boolean correct = false;

        for(int i = 0; i < word.length(); ++i) {
            if(word.charAt(i) == letter) {
                hint.setCharAt(i, letter);
                correct = true;
            }
        }

        if(!correct) {
            ++mistakes;
        }
        ++attempts;

        return correct;
    }

    public boolean isGameLost() {
        return mistakes >= MAX_MISTAKES;
    }

    public boolean isGameWon() {
        return hint.toString().equals(word);
    }

    public boolean isGameOver() {
        return isGameLost() || isGameWon();
    }

    public int getAttempts() {
        return attempts;
    }

    public int getMistakes() {
        return mistakes;
    }

    public String getHint() {
        return hint.toString();
    }

    public String getWord() {
        return word;
    }
}
