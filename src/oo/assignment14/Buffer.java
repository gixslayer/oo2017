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
        // This cast works due to type erasure, which will erase T to Object.
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
            // Block this thread while the buffer is full.
            while (count == buffer.length) {
                bufferFull.await();
            }

            // Store the element, wrapping around the write index if needed.
            buffer[writeIndex] = element;
            writeIndex = (writeIndex + 1) % buffer.length;
            ++count;

            // Notify thread blocked on bufferEmpty.await(), since a new element is available.
            bufferEmpty.signal();
        } finally {
            lock.unlock();
        }
    }

    public T get() throws InterruptedException {
        lock.lock();

        try {
            // Block this thread while the buffer is empty.
            while (count == 0) {
                bufferEmpty.await();
            }

            // Retrieve the element, wrapping around the read index if needed.
            T element = buffer[readIndex];
            readIndex = (readIndex + 1) % buffer.length;
            --count;

            // Notify thread blocked on bufferFull.await(), since space for a new element is available.
            bufferFull.signal();

            return element;
        } finally {
            lock.unlock();
        }
    }
}
