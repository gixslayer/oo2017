package oo.assignment13;

import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Ciske Harsema - s1010048
 * @author Michiel Verloop - s1009995
 */
public class MergeSort {
    private static final ForkJoinPool POOL = new ForkJoinPool();

    public static int[] sort(int[] array) {
        MergeSortTask task = new MergeSortTask(array);

        POOL.execute(task);

        return task.join();
    }

    public static int[] sortAlt(int[] array) {
        MergeSortAltTask task = new MergeSortAltTask(array, 0, array.length);

        POOL.execute(task);

        return task.join();
    }

    public static int[] sortSequential(int[] array) {
        if(array.length < 2) {
            return array;
        } else {
            int[] firstHalf = sortSequential(Arrays.copyOf(array, array.length / 2));
            int[] secondHalf = sortSequential(Arrays.copyOfRange(array, array.length / 2, array.length));

            return merge(firstHalf, secondHalf, array);
        }
    }

    public static int[] sortNonPooled(int[] array) {
        AtomicInteger threadCount = new AtomicInteger();
        MergeSortNonPooled task = new MergeSortNonPooled(array, 0, array.length, threadCount);

        task.run();

        return task.getResult();
    }

    /**
     * merge two sorted arrays: O(N)
     * @param part1 a sorted array
     * @param part2 a sorted array
     * @param dest  destination, length must be >= part1.length + part2.length
     */
    public static int[] merge(int [] part1, int [] part2, int dest[]) {
        int part1Index = 0, part2Index = 0, destIndex = 0;
        while (part1Index < part1.length && part2Index < part2.length) {
            if (part1[part1Index] < part2[part2Index])
                dest[destIndex ++] = part1[part1Index ++];
            else
                dest[destIndex ++] = part2[part2Index ++];
        }
        // copy elements when at most one of the parts contains elements
        while (part1Index < part1.length)
            dest[destIndex ++] = part1[part1Index ++];
        while (part2Index < part2.length)
            dest[destIndex ++] = part2[part2Index ++];

        return dest;
    }

    public static int[] merge(int[] part1, int[] part2) {
        int[] dest = new int[part1.length + part2.length];

        return merge(part1, part2, dest);
    }
}
