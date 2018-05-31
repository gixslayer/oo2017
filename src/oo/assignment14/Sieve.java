package oo.assignment14;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.IntConsumer;

public class Sieve implements Runnable {
    // A higher buffer size will improve efficiency for large prime generation count, but also increases memory footprint.
    public static final int BUFFER_SIZE = 4;

    private final int mod;
    private final Buffer<Integer> input;
    private final IntConsumer onPrimeFound;
    private final ThreadGroup threadGroup;
    private final AtomicBoolean cancellationToken;
    private Buffer<Integer> output;

    public Sieve(int mod, Buffer<Integer> input, IntConsumer onPrimeFound, ThreadGroup threadGroup, AtomicBoolean cancellationToken) {
        this.mod = mod;
        this.input = input;
        this.onPrimeFound = onPrimeFound;
        this.threadGroup = threadGroup;
        this.output = null;
        this.cancellationToken = cancellationToken;
    }

    private void createNextSieve(int value) {
        onPrimeFound.accept(value);

        output = new Buffer<>(BUFFER_SIZE);
        Sieve sieve = new Sieve(value, output, onPrimeFound, threadGroup, cancellationToken);

        new Thread(threadGroup, sieve, "Sieve" + value).start();
    }

    @Override
    public void run() {
        while(!cancellationToken.get()) {
            try {
                int value = input.get();

                if (value % mod != 0) {
                    if (output == null) {
                        createNextSieve(value);
                    }

                    output.put(value);
                }
            } catch (InterruptedException e) {
                cancellationToken.set(true);
            }
        }
    }
}
