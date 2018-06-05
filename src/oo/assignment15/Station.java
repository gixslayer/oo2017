package oo.assignment15;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Ciske Harsema - s1010048
 * @author Michiel Verloop - s1009995
 */
public class Station {
    private final AtomicBoolean closed;
    private final Lock lock;
    private final Condition stationEmpty;
    private final Condition stationNotEmpty;
    private int passengers;

    public Station() {
        this.closed = new AtomicBoolean();
        this.lock = new ReentrantLock();
        this.stationEmpty = lock.newCondition();
        this.stationNotEmpty = lock.newCondition();
        this.passengers = 0;
    }

    public void close() {
        closed.set(true);
    }

    public boolean isClosed() {
        return closed.get();
    }

    public boolean isOpen() {
        return !closed.get();
    }

    public boolean isEmpty() {
        lock.lock();

        try  {
            return passengers == 0;
        } finally {
            lock.unlock();
        }
    }

    public void unload(int numPassengers) {
        lock.lock();

        try {
            while(passengers != 0) {
                try {
                    stationEmpty.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            Utils.log("UNLOAD: %d -> %d", passengers, passengers + numPassengers);

            passengers += numPassengers;
            stationNotEmpty.signalAll();
        } finally {
            lock.unlock();
        }
    }

    public int takePassengers(int max) {
        lock.lock();

        try {
            while (passengers == 0) {
                try {
                    stationNotEmpty.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            int passengersTaken = Math.min(passengers, max);
            Utils.log("TAKEN: %d -> %d", passengers, passengers - passengersTaken);
            passengers -= passengersTaken;

            if(passengers == 0) {
                stationEmpty.signal();
            }

            return passengersTaken;
        } finally {
            lock.unlock();
        }
    }
}
