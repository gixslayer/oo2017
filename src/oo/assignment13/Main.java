package oo.assignment13;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.function.Function;

/**
 * @author Ciske Harsema - s1010048
 * @author Michiel Verloop - s1009995
 */
public class Main {
    private static final Object syncRoot = new Object();

    public static void main(String[] args) {
        //findFile("/home/", "Main.java");
        sort(10_000_000, 1);
    }

    private static void findFile(String path, String fileName) {
        try {
            //FileFinder.findNonPooled(path, fileName, Main::print);
            FileFinder.find(path, fileName, Main::print);
        } catch (FileNotFoundException e) {
            System.out.printf("Could not find directory '%s'\n", path);
        }
    }

    private static void sort(int numElements, long seed) {
        int[] sequentialArray = createRandomArray(numElements, seed);
        int[] parallelArray = createRandomArray(numElements, seed);
        int[] parallelAltArray = createRandomArray(numElements, seed);
        int[] nonPooledArray = createRandomArray(numElements, seed);

        long sequentialTime = timeSort(MergeSort::sortSequential, sequentialArray, "Sequential sort");
        long parallelTime = timeSort(MergeSort::sort, parallelArray, "Parallel sort");
        long parallelAltTime = timeSort(MergeSort::sortAlt, parallelAltArray, "Parallel alternative sort");
        long nonPooledTime = timeSort(MergeSort::sortNonPooled, nonPooledArray, "Parallel non pooled");

        displaySpeedup(sequentialTime, parallelTime, parallelAltTime, nonPooledTime);

        /*
        Example output:

            [Sequential sort]
            Num elements: 5000000
            Sorted: true
            Time taken: 1008 ms

            [Parallel sort]
            Num elements: 5000000
            Sorted: true
            Time taken: 335 ms

            [Parallel alternative sort]
            Num elements: 5000000
            Sorted: true
            Time taken: 238 ms

            [Parallel non pooled]
            Num elements: 5000000
            Sorted: true
            Time taken: 217 ms

            Speedup of parallel sort: 3.01
            Speedup of parallel alternative sort: 4.24
            Speedup of parallel non pooled sort: 4.65
            Available processors: 8

        Ran on a laptop with an old i7, so 4 physical cores (8 logical due to hyper threading).
        */
    }

    private static long timeSort(Function<int[], int[]> sortFunc, int[] array, String header) {
        long start = System.currentTimeMillis();
        int[] sorted = sortFunc.apply(array);
        long time = System.currentTimeMillis() - start;

        displaySortResult(sorted, time, header);

        return time;
    }

    private static void displaySortResult(int[] array, long time, String header) {
        System.out.printf("[%s]\n", header);
        System.out.printf("Num elements: %d\n", array.length);
        System.out.printf("Sorted: %s\n", isSorted(array));
        System.out.printf("Time taken: %d ms\n\n", time);
    }

    private static void displaySpeedup(long sequentialTime, long parallelTime, long parallelAltTime, long nonPooledTime) {
        System.out.printf("Speedup of parallel sort: %.2f\n", (double)sequentialTime / parallelTime);
        System.out.printf("Speedup of parallel alternative sort: %.2f\n", (double)sequentialTime / parallelAltTime);
        System.out.printf("Speedup of parallel non pooled sort: %.2f\n", (double)sequentialTime / nonPooledTime);
        System.out.printf("Available processors: %d\n", Runtime.getRuntime().availableProcessors());
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
