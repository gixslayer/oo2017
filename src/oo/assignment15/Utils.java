package oo.assignment15;

import java.util.concurrent.ThreadLocalRandom;

/**
 * @author Ciske Harsema - s1010048
 * @author Michiel Verloop - s1009995
 */
public class Utils {

    public static int randInt(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max);
    }

    public static void log(String message) {
        // Not necessary in any sensible Java implementation, but sadly not mandated by the standard.
        synchronized (System.out) {
            System.out.println(message);
        }
    }

    public static void log(String format, Object... args) {
        log(String.format(format, args));
    }

    public static void delay(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
