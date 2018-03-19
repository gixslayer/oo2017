package oo.assignment7;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

/**
 * @author Ciske Harsema - s1010048
 * @author Michiel Verloop - s1009995
 */
public class QTree {
    private final QTNode root;
    
    public QTree( Reader input ) throws IOException {
        root = QTNodeFactory.fromInput(new CharReader(input));
    }
    
    public QTree( Bitmap bitmap ) {
        root = QTNodeFactory.fromBitmap(bitmap, 0, 0, bitmap.getSize()).consolidate();
    }

    public void fillBitmap ( Bitmap bitmap ) {
        root.fillBitmap(bitmap, 0, 0, bitmap.getSize());
    }

    public void writeQTree( Writer sb ) throws IOException {
        root.writeNode( sb );
    }
}
