package oo.assignment11;

/**
 * @author Ciske Harsema - s1010048
 * @author Michiel Verloop - s1009995
 */
public class Timer {
    private int timeLeft;
    private double totalTime;

    public Timer() {
        this.timeLeft = 0;
        this.totalTime = 0;
    }

    public void set(int duration) {
        if(duration < 0) {
            throw new IllegalArgumentException("duration cannot be negative");
        }

        totalTime = timeLeft = duration;
    }

    public boolean hasCompleted() {
        return timeLeft == 0;
    }

    public double getTimeLeftFraction() {
        return timeLeft / totalTime;
    }

    public void tick() {
        timeLeft = Math.max(timeLeft - 1, 0);
    }

}
