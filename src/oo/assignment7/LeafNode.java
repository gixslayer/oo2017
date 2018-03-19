package oo.assignment7;

import java.io.IOException;
import java.io.Writer;

/**
 * @author Ciske Harsema - s1010048
 * @author Michiel Verloop - s1009995
 */
public abstract class LeafNode implements QTNode {
    private final boolean value;

    public LeafNode(boolean value) {
        this.value = value;
    }

    @Override
    public void fillBitmap(Bitmap bitmap, int x, int y, int size) {
        bitmap.fillArea(x, y, size, value);
    }

    @Override
    public void writeNode(Writer out) throws IOException {
        out.append(QTNode.LEAF_NODE);
        out.append(value ? QTNode.WHITE_LEAF : QTNode.BLACK_LEAF);
    }

    public boolean getValue() {
        return value;
    }

    public static LeafNode readNode(CharReader reader) throws IOException {
        char c = reader.readChar();

        switch (c) {
            case QTNode.WHITE_LEAF:
                return new WhiteLeaf();
            case QTNode.BLACK_LEAF:
                return new BlackLeaf();
            default:
                throw new IOException(String.format("Unexpected token '%s'", c));
        }
    }
}
