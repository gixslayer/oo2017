package oo.assignment13;

import java.io.File;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * @author Ciske Harsema - s1010048
 * @author Michiel Verloop - s1009995
 */
public class FileFinderNonPooled implements Runnable {
    private static final int THREAD_LIMIT = Runtime.getRuntime().availableProcessors();

    private final File directory;
    private final String fileName;
    private final Consumer<File> onFound;
    private final AtomicInteger threadCount;
    private boolean inThread;

    public FileFinderNonPooled(File directory, String fileName, Consumer<File> onFound, AtomicInteger threadCount) {
        this.directory = directory;
        this.fileName = fileName;
        this.onFound = onFound;
        this.threadCount = threadCount;
        this.inThread = false;
    }

    @Override
    public void run() {
        Function<File, FileFinderNonPooled> createTask = d -> new FileFinderNonPooled(d, fileName, onFound, threadCount);
        Predicate<File> matches = f -> f.getName().equals(fileName);

        File[] content = directory.listFiles();

        stream(content)
                .parallel()
                .filter(File::isFile)
                .filter(matches)
                .forEach(onFound);

        stream(content)
                .filter(File::isDirectory)
                .map(createTask)
                .forEach(task -> {
                    // Only fork if the current search is using less threads than allowed.
                    if(threadCount.getAndIncrement() < THREAD_LIMIT) {
                        task.inThread = true;
                        new Thread(task).run();
                    } else {
                        threadCount.decrementAndGet();
                        task.run();
                    }
                });

        // If this task was a forked task, then the forked thread is about to exit.
        if(inThread) {
            threadCount.decrementAndGet();
        }
    }

    private static <T> Stream<T> stream(T[] elements) {
        return elements != null ? Arrays.stream(elements) : Stream.empty();
    }
}
