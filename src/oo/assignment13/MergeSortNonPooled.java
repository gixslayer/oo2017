package oo.assignment13;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Ciske Harsema - s1010048
 * @author Michiel Verloop - s1009995
 */
public class MergeSortNonPooled implements Runnable {
    private static final int PARALLEL_THRESHOLD = 10000;
    private static final int THREAD_LIMIT = Runtime.getRuntime().availableProcessors();

    private final int[] array;
    private final int offset;
    private final int length;
    private final AtomicInteger threadCount;
    private boolean inThread;
    private int[] result;

    public MergeSortNonPooled(int[] array, int offset, int length, AtomicInteger threadCount) {
        this.array = array;
        this.offset = offset;
        this.length = length;
        this.threadCount = threadCount;
        this.inThread = false;
        this.result = null;
    }

    public int[] getResult() {
        return result;
    }

    @Override
    public void run() {
        if(length < PARALLEL_THRESHOLD) {
            result = MergeSort.sortSequential(Arrays.copyOfRange(array, offset, offset + length));
        } else {
            int leftOffset = offset;
            int leftLength = length / 2;
            int rightOffset = offset + leftLength;
            int rightLength = length - leftLength;

            MergeSortNonPooled leftTask = new MergeSortNonPooled(array, leftOffset, leftLength, threadCount);
            MergeSortNonPooled rightTask = new MergeSortNonPooled(array, rightOffset, rightLength, threadCount);
            Thread thread = null;

            // Try to fork the left task if the current search is under the thread limit.
            if(threadCount.getAndIncrement() < THREAD_LIMIT) {
                leftTask.inThread = true;
                thread = new Thread(leftTask);
                thread.run();
            } else {
                threadCount.decrementAndGet();
                leftTask.run();
            }

            // Always run the right task in the current thread.
            rightTask.run();

            // If the left task was forked, then wait until the computation is ready.
            if(thread != null) {
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            result = MergeSort.merge(leftTask.result, rightTask.result);

            // If this task was a forked task, then the forked thread is about to exit.
            if(inThread) {
                threadCount.decrementAndGet();
            }
        }
    }
}
