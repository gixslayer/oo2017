package oo.assignment14;

import java.util.concurrent.ThreadLocalRandom;
import java.util.function.IntConsumer;
import java.util.stream.IntStream;

/**
 * @author Ciske Harsema - s1010048
 * @author Michiel Verloop - s1009995
 */
public class Main {

    public static void main(String[] args) {
        testBuffer();
    }

    private static void testBuffer() {
        Buffer<Integer> buffer = new Buffer<>(2);
        IntConsumer put = i -> {
            try {
                Thread.sleep(ThreadLocalRandom.current().nextInt(100, 750));
                //System.out.printf("Putting %d\n", i);
                buffer.put(i);
                //System.out.printf("Put %d\n", i);
            } catch (InterruptedException e) {
                System.out.println("Interrupted while putting");
            }
        };

        new Thread(() -> IntStream.rangeClosed(1, 10).forEach(put)).start();

        for(int i = 1; i <= 10; i++) {
            try {
                Thread.sleep(ThreadLocalRandom.current().nextInt(500, 1000));
                //System.out.printf("Getting %d\n", i);
                Integer value = buffer.get();

                assert value == i;

                System.out.printf("Got number %d\n", i);
            } catch (InterruptedException e) {
                System.out.println("Interrupted while getting");
            }
        }
    }
}
