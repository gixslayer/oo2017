package oo.assignment7;

import java.io.IOException;
import java.io.Writer;

/**
 * @author Ciske Harsema - s1010048
 * @author Michiel Verloop - s1009995
 */
public class GrayNode implements QTNode {
    private final QTNode[] nodes;

    private GrayNode() {
        this.nodes = new QTNode[4];
    }

    public GrayNode(Bitmap bitmap, int x, int y, int size) {
        int halfSize = size / 2;

        this.nodes = new QTNode[] {
            QTNodeFactory.fromBitmap(bitmap, x, y, halfSize),
            QTNodeFactory.fromBitmap(bitmap, x + halfSize, y, halfSize),
            QTNodeFactory.fromBitmap(bitmap, x + halfSize, y + halfSize, halfSize),
            QTNodeFactory.fromBitmap(bitmap, x, y + halfSize, halfSize)
        };
    }

    @Override
    public void fillBitmap(Bitmap bitmap, int x, int y, int size) {
        int halfSize = size / 2;

        // Note that the four children are in a clockwise sequence, starting with the top left.
        nodes[0].fillBitmap(bitmap, x, y, halfSize);
        nodes[1].fillBitmap(bitmap, x + halfSize, y, halfSize);
        nodes[2].fillBitmap(bitmap, x + halfSize, y + halfSize, halfSize);
        nodes[3].fillBitmap(bitmap, x, y + halfSize, halfSize);
    }

    @Override
    public void writeNode(Writer out) throws IOException {
        out.append(QTNode.INTERNAL_NODE);

        // Note that the Java specification guarantees this is processed in the correct order.
        for(QTNode node : nodes) {
            node.writeNode(out);
        }
    }

    @Override
    public QTNode consolidate() {
        // Always consolidate child nodes.
        for(int i = 0; i < nodes.length; ++i) {
            nodes[i] = nodes[i].consolidate();
        }

        // Check if all child nodes are leaf nodes of the same type.
        for (int i = 1; i < nodes.length; ++i) {
            QTNode prev = nodes[i - 1];
            QTNode cur = nodes[i];

            if (prev instanceof GrayNode || cur.getClass() != prev.getClass()) {
                return this;
            }
        }

        // If so, return a new leaf node of that type.
        return QTNodeFactory.fromValue(((LeafNode) nodes[0]).getValue());
    }

    public static GrayNode readNode(CharReader reader) throws IOException {
        GrayNode node = new GrayNode();

        for(int i = 0; i < node.nodes.length; ++i) {
            node.nodes[i] = QTNodeFactory.fromInput(reader);
        }

        return node;
    }
}
