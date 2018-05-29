package oo.assignment14;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class PrimeGenerator {
    private final ThreadGroup threadGroup;
    private final Buffer<Integer> buffer;
    private final Sieve sieve;
    private final Thread sieveThread;
    private final Thread generateThread;
    private final AtomicBoolean cancellationToken;
    private final List<Integer> primes;
    private final int count;

    public PrimeGenerator(int count) {
        this.threadGroup = new ThreadGroup("Prime Sieve");
        this.buffer = new Buffer<>(Sieve.BUFFER_SIZE);
        this.cancellationToken = new AtomicBoolean();
        this.primes = new ArrayList<>();
        this.count = count;
        this.sieve = new Sieve(3, buffer, this::onPrimeGenerated, threadGroup, cancellationToken);
        this.sieveThread = new Thread(threadGroup, sieve, "Sieve3");
        this.generateThread = new Thread(threadGroup, this::generateOddNumbers, "OddNumberGenerator");
    }

    private void onPrimeGenerated(int prime) {
        synchronized (primes) {
            if(primes.size() < count) {
                primes.add(prime);
            } else {
                cancellationToken.set(true);
                threadGroup.interrupt();
            }
        }
    }

    private void generateOddNumbers() {
        int value = 3;

        while (!cancellationToken.get()) {
            try {
                buffer.put(value);

                value += 2;
            } catch (InterruptedException e) {
                cancellationToken.set(true);
            }
        }
    }

    public List<Integer> generate() {
        if(count < 2) {
            return Collections.emptyList();
        } else if(count == 2) {
            return Collections.singletonList(2);
        }

        primes.add(2);
        primes.add(3);
        generateThread.start();
        sieveThread.start();

        try {
            generateThread.join();
            sieveThread.join();
        } catch (InterruptedException e) {
            cancellationToken.set(true);
            threadGroup.interrupt();
        }

        return primes;
    }
}
