package oo.assignment7;

import java.io.IOException;
import java.io.Writer;

/**
 * @author Ciske Harsema - s1010048
 * @author Michiel Verloop - s1009995
 */
public interface QTNode {
    char BLACK_LEAF = '0';
    char WHITE_LEAF = '1';
    char LEAF_NODE = '0';
    char INTERNAL_NODE = '1';

    void fillBitmap(Bitmap bitmap, int x, int y, int size);
    void writeNode(Writer out) throws IOException;

    default QTNode consolidate() {
        return this;
    }
}
