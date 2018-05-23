package oo.assignment13;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;

/**
 * @author Ciske Harsema - s1010048
 * @author Michiel Verloop - s1009995
 */
public class Main {
    private static final Object syncRoot = new Object();

    public static void main(String[] args) {
        //findFile("/home/", "Main.java");
        sort(100_000, 1);
    }

    private static void findFile(String path, String fileName) {
        try {
            FileFinder.find(path, fileName, Main::print);
        } catch (FileNotFoundException e) {
            System.out.printf("Could not find directory '%s'\n", path);
        }
    }

    private static void sort(int numElements, long seed) {
        int[] sequentialArray = createRandomArray(numElements, seed);
        int[] parallelArray = createRandomArray(numElements, seed);

        long sequentialTime = timeExecution(() -> MergeSort.sortSequential(sequentialArray));
        long parallelTime = timeExecution(() -> MergeSort.sort(parallelArray));

        displaySortResult(sequentialArray, sequentialTime, "Sequential merge sort");
        displaySortResult(parallelArray, parallelTime, "Parallel merge sort");
        displaySpeedup(parallelTime, sequentialTime);

        /*
        Example output:

            [Sequential merge sort]
            Num elements: 100000
            Sorted: true
            Time taken: 14162 ms

            [Parallel merge sort]
            Num elements: 100000
            Sorted: true
            Time taken: 5361 ms

            Speedup of parallel sort: 2.64
            Available processors: 8

        Ran on a laptop with an old i7, so 4 physical cores (8 logical due to hyper threading).
        */
    }

    private static void displaySortResult(int[] array, long time, String header) {
        System.out.printf("[%s]\n", header);
        System.out.printf("Num elements: %d\n", array.length);
        System.out.printf("Sorted: %s\n", isSorted(array));
        System.out.printf("Time taken: %d ms\n\n", time);
    }

    private static void displaySpeedup(long parallelTime, long sequentialTime) {
        System.out.printf("Speedup of parallel sort: %.2f\n", (double)sequentialTime / parallelTime);
        System.out.printf("Available processors: %d\n", Runtime.getRuntime().availableProcessors());
    }

    private static long timeExecution(Runnable action) {
        long start = System.currentTimeMillis();

        action.run();

        return System.currentTimeMillis() - start;
    }

    private static int[] createRandomArray(int numElements, long seed) {
        int[] array = new int[numElements];
        Random random = new Random(seed);

        for (int i = 0; i < numElements; ++i) {
            array[i] = random.nextInt();
        }

        return array;
    }

    private static void print(File file) {
        // Syncing as this method is invoked from multiple threads.
        synchronized (syncRoot) {
            System.out.printf("Found at %s\n", file.getAbsoluteFile());
        }
    }

    private static boolean isSorted(int[] elements) {
        if(elements.length < 2) {
            return true;
        }

        for(int i = 1; i < elements.length; ++i) {
            if(elements[i-1] > elements[i]) {
                return false;
            }
        }

        return true;
    }
}
