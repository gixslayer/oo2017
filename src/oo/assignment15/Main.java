package oo.assignment15;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author Ciske Harsema - s1010048
 * @author Michiel Verloop - s1009995
 */
public class Main {

    public static void main(String[] args) {
        ExecutorService executor = Executors.newCachedThreadPool();
        Station station = new Station();
        Train train = new Train(10, station);
        List<Taxi> taxis = Arrays.asList(
                new Taxi(1, 5, station),
                new Taxi(2, 5, station),
                new Taxi(3, 7, station),
                new Taxi(4, 7, station));

        taxis.forEach(executor::submit);
        executor.submit(train);

        executor.shutdown();
        try {
            executor.awaitTermination(5, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Print statistics
        System.out.println("\n[STATISTICS]");
        System.out.printf("Total train transport time: %d\n", train.getTotalTransportTime());
        System.out.printf("Total train passengers: %d\n", train.getTotalPassengers());
        System.out.printf("Total taxi transport time: %d\n", taxis.stream().map(Taxi::getTotalTransportTime).reduce(0, Integer::sum));
        System.out.printf("Total taxi passengers: %d\n", taxis.stream().map(Taxi::getTotalPassengers).reduce(0, Integer::sum));
        taxis.forEach(taxi -> {
            System.out.printf("Taxi %d total transport time: %d\n", taxi.getTaxiNumber(), taxi.getTotalTransportTime());
            System.out.printf("Taxi %d total passengers: %d\n", taxi.getTaxiNumber(), taxi.getTotalPassengers());
        });
    }
}
