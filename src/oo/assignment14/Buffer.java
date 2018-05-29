package oo.assignment14;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Ciske Harsema - s1010048
 * @author Michiel Verloop - s1009995
 */
public class Buffer<T> {
    private final T[] buffer;
    private final Lock lock;
    private final Condition bufferFull;
    private final Condition bufferEmpty;
    private int readIndex;
    private int writeIndex;
    private int count;

    public Buffer(int size) {
        //noinspection unchecked
        this.buffer = (T[])new Object[size];
        this.lock = new ReentrantLock();
        this.bufferFull = lock.newCondition();
        this.bufferEmpty = lock.newCondition();
        this.readIndex = 0;
        this.writeIndex = 0;
        this.count = 0;
    }

    public void put(T element) throws InterruptedException {
        lock.lock();

        try {
            while (count == buffer.length) {
                bufferFull.await();
            }

            buffer[writeIndex] = element;
            writeIndex = (writeIndex + 1) % buffer.length;
            ++count;

            bufferEmpty.signal();
        } finally {
            lock.unlock();
        }
    }

    public T get() throws InterruptedException {
        lock.lock();

        try {
            while (count == 0) {
                bufferEmpty.await();
            }

            T element = buffer[readIndex];
            readIndex = (readIndex + 1) % buffer.length;
            --count;

            bufferFull.signal();

            return element;
        } finally {
            lock.unlock();
        }
    }
}
