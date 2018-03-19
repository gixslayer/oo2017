package oo.assignment7;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

/**
 * @author Ciske Harsema - s1010048
 * @author Michiel Verloop - s1009995
 */
public class QTreeTester {
    public static void test(String configuration, int size) {
        StringReader reader = new StringReader(configuration);
        StringWriter writer1 = new StringWriter();
        StringWriter writer2 = new StringWriter();
        Bitmap bitmap1 = new Bitmap(size);
        Bitmap bitmap2 = new Bitmap(size);

        try {
            QTree qTree1 = new QTree(reader);

            qTree1.fillBitmap(bitmap1);
            qTree1.writeQTree(writer1);

            QTree qTree2 = new QTree(bitmap1);

            qTree2.fillBitmap(bitmap2);
            qTree2.writeQTree(writer2);

            String config1 = writer1.toString();
            String config2 = writer2.toString();

            System.out.println("[Tree 1]");
            System.out.printf("Built from config: %s\n", configuration);
            System.out.printf("Resulting config:  %s\n", config1);
            System.out.println("Resulting bitmap:");
            System.out.println(bitmap1);

            System.out.println("[Tree 2]");
            System.out.println("Built from previous bitmap");
            System.out.printf("Resulting config: %s\n", config2);
            System.out.println("Resulting bitmap:");
            System.out.println(bitmap2);

            System.out.printf("Resulting configs equal: %s\n", config1.equals(config2) ? "yes" : "no");
            System.out.printf("Resulting bitmaps equal: %s\n", bitmap1.equals(bitmap2) ? "yes" : "no");
        } catch (IOException ex) {
            System.err.printf("Error reading QTree: %s\n", ex.getLocalizedMessage());
        }
    }
}
