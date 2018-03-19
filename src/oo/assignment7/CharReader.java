package oo.assignment7;

import java.io.IOException;
import java.io.Reader;

/**
 * @author Ciske Harsema - s1010048
 * @author Michiel Verloop - s1009995
 */
public class CharReader {
    private final Reader reader;

    public CharReader(Reader reader) {
        this.reader = reader;
    }

    public char readChar() throws IOException {
        int value = reader.read();

        if(value == -1) {
            throw new IOException("Unexpected end of stream");
        }

        return (char)value;
    }
}
