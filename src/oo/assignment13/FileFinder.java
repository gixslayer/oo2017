package oo.assignment13;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

/**
 * @author Ciske Harsema - s1010048
 * @author Michiel Verloop - s1009995
 */
public class FileFinder {
    private static final ForkJoinPool POOL = new ForkJoinPool();

    public static void find(String path, String fileName, Consumer<File> onFound) throws FileNotFoundException {
        File directory = new File(path);

        if(!directory.exists() || !directory.isDirectory()) {
            throw new FileNotFoundException("directory does not exist, or is not a directory");
        }

        find(directory, fileName, onFound);
    }

    public static void find(File directory, String fileName, Consumer<File> onFound) {
        FileFinderTask task = new FileFinderTask(directory, fileName, onFound);

        POOL.execute(task);

        task.join();
    }

    public static void findNonPooled(String path, String fileName, Consumer<File> onFound) throws FileNotFoundException {
        File directory = new File(path);

        if(!directory.exists() || !directory.isDirectory()) {
            throw new FileNotFoundException("directory does not exist, or is not a directory");
        }

        findNonPooled(directory, fileName, onFound);
    }

    public static void findNonPooled(File directory, String fileName, Consumer<File> onFound) {
        AtomicInteger threadCount = new AtomicInteger();
        FileFinderNonPooled fileFinder = new FileFinderNonPooled(directory, fileName, onFound, threadCount);

        fileFinder.run();
    }
}
