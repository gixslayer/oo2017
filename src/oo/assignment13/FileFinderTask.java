package oo.assignment13;

import java.io.File;
import java.util.Arrays;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Ciske Harsema - s1010048
 * @author Michiel Verloop - s1009995
 */
public class FileFinderTask extends RecursiveAction {
    private final File directory;
    private final String fileName;
    private final Consumer<File> onFound;

    public FileFinderTask(File directory, String fileName, Consumer<File> onFound) {
        this.directory = directory;
        this.fileName = fileName;
        this.onFound = onFound;
    }

    @Override
    protected void compute() {
        Function<File, FileFinderTask> createTask = d -> new FileFinderTask(d, fileName, onFound);
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
                .map(ForkJoinTask::fork)
                .collect(Collectors.toList()) // Ensures all tasks are forked before joining.
                .forEach(ForkJoinTask::join);
    }

    private static <T> Stream<T> stream(T[] elements) {
        return elements != null ? Arrays.stream(elements) : Stream.empty();
    }
}
