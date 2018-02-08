package oo.assignment2;

import oo.WordReader;

/**
 * @author Ciske Harsema - s1010048
 */
public class Main {
    public static final WordReader WORD_READER = new WordReader("words.txt");

    public static void main(String[] args) {
        new GallowsTUI().run();
    }
}
