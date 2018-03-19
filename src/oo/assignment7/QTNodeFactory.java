package oo.assignment7;

import java.io.IOException;

/**
 * @author Ciske Harsema - s1010048
 * @author Michiel Verloop - s1009995
 */
public class QTNodeFactory {
    public static QTNode fromInput(CharReader reader) throws IOException {
        char c = reader.readChar();

        switch (c) {
            case QTNode.INTERNAL_NODE:
                return GrayNode.readNode(reader);
            case QTNode.LEAF_NODE:
                return LeafNode.readNode(reader);
            default:
                throw new IOException(String.format("Unexpected token '%s'", c));
        }
    }

    public static QTNode fromValue(boolean value) {
        return value ? new WhiteLeaf() : new BlackLeaf();
    }

    public static QTNode fromBitmap(Bitmap bitmap, int x, int y, int size) {
        return size == 1 ? fromValue(bitmap.getBit(x, y)) : new GrayNode(bitmap, x, y, size);
    }
}
