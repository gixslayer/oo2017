package oo.assignment15;

/**
 * @author Ciske Harsema - s1010048
 * @author Michiel Verloop - s1009995
 */
public class Taxi implements Runnable {
    private final int taxiNumber;
    private final int capacity;
    private final Station station;
    private int totalPassengers;
    private int totalTransportTime;

    public Taxi(int number, int capacity, Station station) {
        this.taxiNumber = number;
        this.capacity = capacity;
        this.station = station;
        this.totalPassengers = 0;
        this.totalTransportTime = 0;
    }

    @Override
    public void run() {
        while(station.isOpen() || !station.isEmpty()) {
            transport();
        }
    }

    private int takePassengers() {
        return station.takePassengers(capacity);
    }

    private void transport() {
        int passengers = takePassengers();
        totalPassengers += passengers;

        Utils.log("Taxi %d picked up %d passengers", taxiNumber, passengers);

        simulateTransport();
    }

    private void simulateTransport() {
        int delay = Utils.randInt(250, 500);
        totalTransportTime += delay;

        Utils.log("Taxi %d leaving, returns in %d ms", taxiNumber, delay);

        Utils.delay(delay);
    }

    public int getTotalPassengers() {
        return totalPassengers;
    }

    public int getTotalTransportTime() {
        return totalTransportTime;
    }

    public int getTaxiNumber() {
        return taxiNumber;
    }
}
