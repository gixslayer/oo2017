package oo.assignment13;

import java.util.Arrays;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

public class MergeSortAltTask extends RecursiveTask<int[]> {
    private static final int PARALLEL_THRESHOLD = 10000;

    private final int[] array;
    private final int offset;
    private final int length;

    public MergeSortAltTask(int[] array, int offset, int length) {
        this.array = array;
        this.offset = offset;
        this.length = length;
    }

    @Override
    protected int[] compute() {
        if(length < PARALLEL_THRESHOLD) {
            return MergeSort.sortSequential(Arrays.copyOfRange(array, offset, offset + length));
        } else {
            int leftOffset = offset;
            int leftLength = length / 2;
            int rightOffset = offset + leftLength;
            int rightLength = length - leftLength;

            ForkJoinTask<int[]> leftTask = new MergeSortAltTask(array, leftOffset, leftLength).fork();
            ForkJoinTask<int[]> rightTask = new MergeSortAltTask(array, rightOffset, rightLength).fork();

            // Since elements are merged into a temporary array, and the splitting doesn't make any copies, a lot of data
            // copies are eliminated compared with the 'regular' implementation. Not only does this decrease the memory
            // footprint as the GC can reclaim these temporary copies once processed, but it also reduces the amount of
            // work to be performed by having eliminated many copy operations.
            return MergeSort.merge(leftTask.join(), rightTask.join());
        }
    }
}
