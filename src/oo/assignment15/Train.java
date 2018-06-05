package oo.assignment15;

import java.util.stream.IntStream;

/**
 * @author Ciske Harsema - s1010048
 * @author Michiel Verloop - s1009995
 */
public class Train implements Runnable {
    private final int numTransportation;
    private final Station station;
    private int totalPassengers;
    private int totalTransportTime;

    public Train(int numTransportation, Station station) {
        this.numTransportation = numTransportation;
        this.station = station;
        this.totalPassengers = 0;
        this.totalTransportTime = 0;
    }

    @Override
    public void run() {
        IntStream.rangeClosed(1, numTransportation).forEach(this::transport);

        station.close();
    }

    private void transport(int number) {
        simulateTransport(number);

        int passengers = Utils.randInt(60, 80);
        totalPassengers += passengers;

        Utils.log("Train %d arrived with %d passengers", number, passengers);
        station.unload(passengers);
        Utils.log("Train %d unloaded", number);
    }

    private void simulateTransport(int number) {
        int delay = Utils.randInt(500, 1000);
        totalTransportTime += delay;

        Utils.log("Train %d leaving, arrival in %d ms", number, delay);

        Utils.delay(delay);
    }

    public int getTotalPassengers() {
        return totalPassengers;
    }

    public int getTotalTransportTime() {
        return totalTransportTime;
    }
}
