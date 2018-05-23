package oo.assignment13;

import java.util.Arrays;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

/**
 * @author Ciske Harsema - s1010048
 * @author Michiel Verloop - s1009995
 */
public class MergeSortTask extends RecursiveTask<int[]> {
    private static final int PARALLEL_THRESHOLD = 5000;

    private final int[] array;

    public MergeSortTask(int[] array) {
        this.array = array;
    }

    @Override
    protected int[] compute() {
        if(array.length < PARALLEL_THRESHOLD) {
            return MergeSort.sortSequential(array);
        } else {
            int[] firstHalf = Arrays.copyOf(array, array.length / 2);
            int[] secondHalf = Arrays.copyOfRange(array, array.length / 2, array.length);

            ForkJoinTask<int[]> firstTask = new MergeSortTask(firstHalf).fork();
            ForkJoinTask<int[]> secondTask = new MergeSortTask(secondHalf).fork();

            return MergeSort.merge(firstTask.join(), secondTask.join(), array);
        }
    }
}
